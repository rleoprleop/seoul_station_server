package com.capstone.capstone.interceptor;

import com.capstone.capstone.dto.CommonCode;
import com.capstone.capstone.dto.CommonCodeDTO;
import com.capstone.capstone.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RefreshInterceptor implements HandlerInterceptor {
    private final RedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();

        String userId = request.getHeader("UserId");
        String token = jwtTokenProvider.getTokenFromHeader(request.getHeader("Authorization"));
        log.info("refresh interceptor {}, {}",userId, token);
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(userId);//redis에 저장된 refresh token
        log.info("{}",value);
        assert value != null;
        if(!value.equals(token)){//저장된 refresh token과 request에 있는 token 비교. (가장 최근에 발급된 토큰인지 확인)
            log.info("userId check");
            //재 로그인
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.REFRESH_TOKEN_INVALID);
            resultMap.put("code",setCode(commonCodeDTO));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();

            JSONObject jsonObject = new JSONObject(resultMap);

            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();

            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    public Map<String,Object> setCode(CommonCodeDTO commonCodeDTO){
        HashMap<String, Object> code = new HashMap<>();

        code.put("message", commonCodeDTO.getMessage());
        code.put("status",commonCodeDTO.getStatus());
        return code;
    }
}
