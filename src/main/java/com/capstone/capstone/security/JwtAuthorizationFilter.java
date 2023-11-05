package com.capstone.capstone.security;


import com.capstone.capstone.dto.CommonCode;
import com.capstone.capstone.dto.CommonCodeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {
        // 1. Request Header 에서 JWT 토큰 추출
        String header = request.getHeader("Authorization");
        List<String> list = Arrays.asList(
                "/user/sign-in",
                "/user/sign-up"
        );

        System.out.println(request.getRequestURI());

        // 2. 토큰이 필요하지 않은 API URL의 경우 => 로직 처리 없이 다음 필터로 이동
        if (list.contains(request.getRequestURI())) {
            System.out.println("1111"+request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }
        /*String uri=((HttpServletRequest) request).getRequestURI();
        System.out.println("PATH"+uri);

        HttpServletResponse res = (HttpServletResponse) response;
        if((token==null||!jwtTokenProvider.validateToken(token))&&(uri!="/user/sign-up" || uri!="/user/sign-in")){
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            String data=new ObjectMapper().writeValueAsString(CommonCodeDTO.toCommonCodeDTO(CommonCode.FAIL_AUTHENTICATION));
            res.getWriter().write(data);
        }// 2. validateToken 으로 토큰 유효성 검사
        else*/
        //...
        if (header != null && !header.equalsIgnoreCase("")) {
//            BufferedReader br=null;
//            String line="";
//            StringBuilder stringBuilder = new StringBuilder();
//            String bodyJson="";
//            try{
//                InputStream inputStream = request.getInputStream();
//                if(inputStream != null){
//                    br=new BufferedReader(new InputStreamReader(inputStream));
//                    while((line=br.readLine())!=null){
//                        stringBuilder.append(line);
//                    }
//                }
//                else{
//                    log.info("데이터 없음");
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            bodyJson = stringBuilder.toString();
//            JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = (JSONObject) jsonParser.parse(bodyJson);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            String userId = (String) jsonObject.get("userId");
            // [STEP2] Header 내에 토큰을 추출합니다.
            String token = jwtTokenProvider.getTokenFromHeader(header);

            // [STEP3] 추출한 토큰이 유효한지 여부를 체크합니다.
            if (jwtTokenProvider.validateToken(token)) {

                // [STEP4] 토큰을 기반으로 사용자 아이디를 반환 받는 메서드
                String tokenUserId = jwtTokenProvider.getUserIdFromToken(token);
//                log.info("userId Check: {}, {}",tokenUserId, userId);
//
//                if(tokenUserId != userId){
//                    setErrorResponse(response, CommonCode.TOKEN_INVALID);
//                }
                // [STEP5] 사용자 아이디가 존재하는지 여부 체크
                if (tokenUserId != null && !tokenUserId.equalsIgnoreCase("")) {
                    chain.doFilter(request, response);
                } else {
                    setErrorResponse(response, CommonCode.NOT_FOUND_USER_ID);
                }
                // 토큰이 유효하지 않은 경우
            } else {
                setErrorResponse(response, CommonCode.TOKEN_INVALID);
            }
        }
        // [STEP2-1] 토큰이 존재하지 않는 경우
        else {
            setErrorResponse(response, CommonCode.NOT_FOUND_TOKEN);
        }
        /*if(header != null && JwtTokenProvider.validateToken(header)){
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
            try{
                authentication = JwtTokenProvider.getAuthentication(header);
                System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e){
                //토큰의 유효기간 만료
                System.out.println(e);
                setErrorResponse(response, CommonCode.TOKEN_EXPIRED);
            } catch (JwtException | IllegalArgumentException e){
                //유효하지 않은 토큰
                System.out.println(e.getMessage());
                setErrorResponse(response, CommonCode.TOKEN_INVALID);
            } catch (NoSuchElementException e){
                setErrorResponse(response,CommonCode.NOT_FOUND_USER_ID);
            }
        }
        chain.doFilter(request, response);*/
    }

    // Request Header 에서 토큰 정보 추출
    private void setErrorResponse(HttpServletResponse response, CommonCode commonCode){
        HashMap<String, Object> responseMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(commonCode);
        responseMap.put("code",setCommonCode(commonCodeDTO));
        try{
            response.getWriter().write(objectMapper.writeValueAsString(responseMap));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public Map<String,Object> setCommonCode(CommonCodeDTO commonCodeDTO){
        HashMap<String, Object> code = new HashMap<>();

        code.put("message", commonCodeDTO.getMessage());
        code.put("status",commonCodeDTO.getStatus());
        return code;
    }

}