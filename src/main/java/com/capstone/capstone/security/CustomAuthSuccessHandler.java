package com.capstone.capstone.security;

import com.capstone.capstone.VO.PlayerVO;
import com.capstone.capstone.VO.UserVO;
import com.capstone.capstone.dto.*;
//import com.capstone.capstone.entity.UserEntity;
//import com.capstone.capstone.repository.UserRepository;
import com.capstone.capstone.mapper.PlayerMapper;
import com.capstone.capstone.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserMapper userMapper;
    private final PlayerMapper playerMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.debug("3.CustomLoginSuccessHandler");

        System.out.println("EE"+(authentication.getName()));
        Optional<UserVO> byUserId = userMapper.getUserByUserId(authentication.getName());
        System.out.println(byUserId);
        UserVO userVO = byUserId.get();
        System.out.println(userVO.getUserId()+userVO.getUserPassword()+userVO.getNickName());
        Optional<PlayerVO> playerVOOptional = playerMapper.getPlayerById(userVO.getId());
        PlayerVO playerVO = playerVOOptional.get();
        HashMap<String, Object> responseMap = new HashMap<>();

        JSONObject jsonObject;
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_SIGN_IN);
        responseMap.put("code",setCommonCode(commonCodeDTO));
        responseMap.put("player",setPlayerCode(playerVO));
        responseMap.put("user", setUserCode(userVO));
        jsonObject = new JSONObject(responseMap);

        String token = JwtTokenProvider.generateToken(userVO);
        jsonObject.put("token", token);


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);  // 최종 저장된 '사용자 정보', '사이트 정보' Front 전달
        printWriter.flush();
        printWriter.close();
    }

    public Map<String,Object> setUserCode(UserVO userVO) {
        HashMap<String, Object> code = new HashMap<>();
        String userCreateDate = userVO.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm:ss"));
        code.put("id",userVO.getId());
        code.put("userName",userVO.getNickName());
        code.put("userCreateDate",userCreateDate);

        return code;
    }

    public Map<String,Object> setPlayerCode(PlayerVO playerVO) {
        HashMap<String, Object> code = new HashMap<>();
        code.put("health",playerVO.getHealth());
        code.put("stage",playerVO.getStage());

        return code;
    }

    public Map<String,Object> setCommonCode(CommonCodeDTO commonCodeDTO){
        HashMap<String, Object> code = new HashMap<>();

        code.put("message", commonCodeDTO.getMessage());
        code.put("status",commonCodeDTO.getStatus());
        return code;
    }
}