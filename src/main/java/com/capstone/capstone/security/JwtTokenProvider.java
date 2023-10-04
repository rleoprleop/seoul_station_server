package com.capstone.capstone.security;

import com.capstone.capstone.VO.UserVO;
import com.capstone.capstone.dto.TokenInfo;
import com.capstone.capstone.dto.UserDTO;
//import com.capstone.capstone.entity.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenProvider {

    private static final String secretKey="seA3S45Enke7sla4Ase6k5pqEW2e35485t0eFEFlksdvuJI34iroOa354gj73653305s8k6dRW6n5x24m1cz";
    private static final int tokenTime = 10 * 60 * 100000; //분 초. 10분
    private static Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));


    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public static String generateToken(UserVO userVO){
        // 권한 가져오기
        /*String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + tokenTime);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + tokenTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();*/
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())                              // Header 구성
                .setClaims(createClaims(userVO))                       // Payload - Claims 구성
                .setSubject(String.valueOf(userVO.getUserId()))        // Payload - Subject 구성
                .signWith(key, SignatureAlgorithm.HS256)  // Signature 구성
                .setExpiration(createExpiredDate());                    // Expired Date 구성
        return builder.compact();
    }

    private static Date createExpiredDate() {
        long now = (new Date()).getTime();
        Date date = new Date(now + tokenTime);
        return date;
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(UserVO userVO) {
        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();

        log.info("userId :" + userVO.getUserId());
        log.info("nickName :" + userVO.getNickName());

        claims.put("userId", userVO.getUserId());
        claims.put("nickName", userVO.getNickName());
        return claims;
    }

    /*// JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public static Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        System.out.println("EEEEEEE"+principal);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }*/

    // 토큰 정보를 검증하는 메서드
    public static boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token Expired "+ e);
            return false;
        } catch (JwtException e) {
            System.out.println("Token Tampered "+ e);
            return false;
        } catch (NullPointerException e) {
            System.out.println("Token is null "+ e);
            return false;
        }
    }

    private static Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public static String getUserIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.get("userId").toString();
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }
}