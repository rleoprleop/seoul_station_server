package com.capstone.capstone.interceptor;

import com.capstone.capstone.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(jwtTokenProvider))
                .excludePathPatterns("/user/sign-in", "/user/sign-up");
        registry.addInterceptor(new RefreshInterceptor(redisTemplate,jwtTokenProvider))
                .addPathPatterns("/user/refresh");
    }
}