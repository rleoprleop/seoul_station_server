package com.capstone.capstone.security;

import com.capstone.capstone.dto.CommonCode;
import com.capstone.capstone.dto.CommonCodeDTO;
import com.capstone.capstone.dto.UserPasswordChangeDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Configuration
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        // [STEP1] 클라이언트로 전달 할 응답 값을 구성합니다.
        HashMap<String, Object> resultMap = new HashMap<>();


        // [STEP2] 발생한 Exception 에 대해서 확인합니다.
        if (exception instanceof AuthenticationServiceException) {
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.NOT_FOUND_USER_ID);
            resultMap.put("code",setCode(commonCodeDTO));

        } else if (exception instanceof BadCredentialsException) {
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.ERROR_PASSWORD);
            resultMap.put("code",setCode(commonCodeDTO));

        } else if (exception instanceof LockedException) {
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.FAIL_AUTHENTICATION);
            resultMap.put("code",setCode(commonCodeDTO));

        } else if (exception instanceof DisabledException) {
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.FAIL_AUTHENTICATION);
            resultMap.put("code",setCode(commonCodeDTO));

        } else if (exception instanceof AccountExpiredException) {
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.FAIL_AUTHENTICATION);
            resultMap.put("code",setCode(commonCodeDTO));

        } else if (exception instanceof CredentialsExpiredException) {
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.FAIL_AUTHENTICATION);
            resultMap.put("code",setCode(commonCodeDTO));

        } else {
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.FAIL_AUTHENTICATION);
            resultMap.put("code",setCode(commonCodeDTO));

        }
        // [STEP4] 응답 값을 구성하고 전달합니다.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        resultMap.put("userInfo", null);
        resultMap.put("resultCode", 80);
        JSONObject jsonObject = new JSONObject(resultMap);

        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
    public Map<String,Object> setCode(CommonCodeDTO commonCodeDTO){
        HashMap<String, Object> code = new HashMap<>();

        code.put("message", commonCodeDTO.getMessage());
        code.put("status",commonCodeDTO.getStatus());
        return code;
    }

    }