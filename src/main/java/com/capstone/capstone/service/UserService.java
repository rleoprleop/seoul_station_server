package com.capstone.capstone.service;

import com.capstone.capstone.dto.*;
import com.capstone.capstone.entity.UserEntity;
import com.capstone.capstone.repository.UserRepository;
import com.capstone.capstone.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
//import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;


    public Map<String,Object> signUp(UserDTO userDTO) { // 유저 회원가입
        Map<String, Object> result = new HashMap<>();
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDTO.getUserId());
        Optional<UserEntity> byNickName = userRepository.findByNickName(userDTO.getNickName());

        if(byUserId.isPresent()){//같은 userId 있을 경우
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SAME_USER_ID);
            result.put("code",commonCodeDTO);
            return result;
        }
        if(byNickName.isPresent()){//같은 nickName 있을 경우
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SAME_USER_NAME);
            result.put("code",commonCodeDTO);
            return result;
        }
        // 1. dto -> entity 변환
        UserEntity userEntity = UserEntity.builder()
                .userId(userDTO.getUserId())
                .userPassword(passwordEncoder.encode(userDTO.getUserPassword()))
                .nickName(userDTO.getNickName())
                .roles(Collections.singletonList("USER")) // 최초 가입시 USER 로 설정
                .build();
        // 2. repository의 save 호출
        UserEntity save = userRepository.save(userEntity);// entity 객체를 넘겨줘야 함

        UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(save);
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_SIGN_UP);
        result.put("data",userResponseDTO);
        result.put("code",commonCodeDTO);
        return  result;
    }

    public Map<String,Object> signIn(UserDTO userDTO) {
        Map<String, Object> result = new HashMap<>();

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUserId(), userDTO.getUserPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("22222"+authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        System.out.println("33333"+tokenInfo);

        // 1. userId로 DB조회
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDTO.getUserId());
        // 2. DB에 저장된 password와 일치하는지 판단
        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            if(passwordEncoder.matches(userDTO.getUserPassword(),userEntity.getPassword())){ // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(userEntity);
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_SIGN_IN);
                result.put("data",userResponseDTO);
                result.put("code",commonCodeDTO);
                result.put("token",tokenInfo);
            }
            else{ // 비밀번호 불일치
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.ERROR_PASSWORD);
                result.put("code",commonCodeDTO);
            }
        }
        else {// 조회 결과 X
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.NOT_FOUND_USER_ID);
            result.put("code",commonCodeDTO);
        }
        return result;
    }

    @Transactional // DB update
    public Map<String,Object> passwordChange(UserPasswordChangeDTO userPasswordChangeDTO){
        Map<String, Object> result = new HashMap<>();
        Optional<UserEntity> byUserId = userRepository.findByUserId(userPasswordChangeDTO.getUserId());

        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            if(passwordEncoder.matches(userPasswordChangeDTO.getUserPassword(),userEntity.getPassword())){ // 비밀번호 일치
                // userPassword를 newUserPassword로 DB업데이트
                userEntity.setUserPassword(passwordEncoder.encode(userPasswordChangeDTO.getNewUserPassword()));
                // entity -> dto 변환 후 리턴
                UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(userEntity);
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_PASSWORD_CHANGE);
                result.put("data",userResponseDTO);
                result.put("code",commonCodeDTO);
            }
            else{ // 비밀번호 불일치
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.ERROR_PASSWORD);
                result.put("code",commonCodeDTO);
            }
        }
        else {// 조회 결과 X
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.NOT_FOUND_USER_ID);
            result.put("code",commonCodeDTO);
        }
        return result;
    }

    public Map<String,Object> deleteUser(UserDeleteDTO userDeleteDTO) {
        Map<String, Object> result = new HashMap<>();
        // 1. userId로 DB조회
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDeleteDTO.getUserId());

        // 2. DB에 저장된 password와 일치하는지 판단
        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            System.out.println(passwordEncoder.encode(userEntity.getPassword())+" && "+userDeleteDTO.getUserPassword());
            if(passwordEncoder.matches(userDeleteDTO.getUserPassword(),userEntity.getPassword())){ // 비밀번호 일치
                System.out.println(passwordEncoder.encode(userEntity.getPassword())+" && "+userDeleteDTO.getUserPassword());

                userRepository.delete(userEntity);//delete
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_DELETE);
                result.put("code",commonCodeDTO);
            }
            else{ // 비밀번호 불일치
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.ERROR_PASSWORD);
                result.put("code",commonCodeDTO);
            }
        }
        else {// 조회 결과 X
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.NOT_FOUND_USER_ID);
            result.put("code",commonCodeDTO);
        }
        return result;
    }
}
