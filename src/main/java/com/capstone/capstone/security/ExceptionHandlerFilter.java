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
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
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
    private void setErrorResponse(HttpServletResponse response, CommonCode commonCode){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(commonCode);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(commonCodeDTO));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
