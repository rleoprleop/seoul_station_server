package com.capstone.capstone.interceptor;

import com.capstone.capstone.dto.CommonCode;
import com.capstone.capstone.dto.CommonCodeDTO;
import com.capstone.capstone.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("===============================================");
        log.info("==================== BEGIN ====================");
        log.info("Request URI ===> " + request.getRequestURI());
        HashMap<String, Object> resultMap = new HashMap<>();

        if(!request.getHeader("userId").equals(jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.getTokenFromHeader(request.getHeader("Authorization"))))){
            log.info("userId check");
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.TOKEN_INVALID);
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
        log.info("==================== END ======================");
        log.info("===============================================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    public Map<String,Object> setCode(CommonCodeDTO commonCodeDTO){
        HashMap<String, Object> code = new HashMap<>();

        code.put("message", commonCodeDTO.getMessage());
        code.put("status",commonCodeDTO.getStatus());
        return code;
    }
}