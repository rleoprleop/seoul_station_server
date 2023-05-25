package com.capstone.capstone.security;

import com.capstone.capstone.dto.*;
import com.capstone.capstone.entity.UserEntity;
import com.capstone.capstone.repository.UserRepository;
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

    private final UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.debug("3.CustomLoginSuccessHandler");

        System.out.println("EE"+(authentication.getName()));
        Optional<UserEntity> byUserId = userRepository.findByUserId(authentication.getName());
        System.out.println(byUserId);
        UserEntity userEntity = byUserId.get();

        HashMap<String, Object> responseMap = new HashMap<>();

        JSONObject jsonObject;
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_SIGN_IN);
        responseMap.put("code",setCommonCode(commonCodeDTO));
        UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(userEntity);

        responseMap.put("data", setResponseCode(userResponseDTO));
        jsonObject = new JSONObject(responseMap);

        String token = JwtTokenProvider.generateToken(userEntity);
        jsonObject.put("token", token);


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);  // 최종 저장된 '사용자 정보', '사이트 정보' Front 전달
        printWriter.flush();
        printWriter.close();
    }

    public Map<String,Object> setResponseCode(UserResponseDTO userResponseDTO) {
        HashMap<String, Object> code = new HashMap<>();
        String userCreateDate = userResponseDTO.getUserCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm:ss"));
        code.put("id",userResponseDTO.getId());
        code.put("userName",userResponseDTO.getUserName());
        code.put("userCreateDate",userCreateDate);

        return code;
    }

    public Map<String,Object> setCommonCode(CommonCodeDTO commonCodeDTO){
        HashMap<String, Object> code = new HashMap<>();

        code.put("message", commonCodeDTO.getMessage());
        code.put("status",commonCodeDTO.getStatus());
        return code;
    }
}