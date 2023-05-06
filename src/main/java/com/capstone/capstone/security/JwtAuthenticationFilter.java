package com.capstone.capstone.security;

import com.capstone.capstone.dto.CommonCode;
import com.capstone.capstone.dto.CommonCodeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 1. Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);
        HttpServletResponse res = (HttpServletResponse) response;
        Authentication authentication;
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
        if(token != null && jwtTokenProvider.validateToken(token)){
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
            try{
                authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (IllegalArgumentException e) {
                System.out.println("유효하지 않은 토큰");
                throw e;
            } catch (ExpiredJwtException e) {
                System.out.println("토큰 기한 만료");
                throw e;
            } catch(SignatureException e){
                System.out.println("사용자 인증 실패");
                throw e;
            }
        }
        chain.doFilter(request, res);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}