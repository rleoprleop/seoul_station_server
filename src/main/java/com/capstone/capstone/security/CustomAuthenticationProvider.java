package com.capstone.capstone.security;

//import com.capstone.capstone.dto.UserDetailsDTO;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService userDetailsService;

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("2.CustomAuthenticationProvider");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // 'AuthenticaionFilter' 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String userId = token.getName();
        String userPw = (String) token.getCredentials();

        System.out.println(userId+", "+userPw);

        // Spring Security - UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        System.out.println("11"+userId+" "+userDetails.getPassword()+" "+userPw);
        if (!(passwordEncoder.matches(userPw, userDetails.getPassword()))) {
            System.out.println("Eeee");
            throw new BadCredentialsException(userDetails.getPassword() + "Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userPw, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}