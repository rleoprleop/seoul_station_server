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
        log.info("Fail");

        HashMap<String, Object> resultMap = new HashMap<>();


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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        resultMap.put("data", null);
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