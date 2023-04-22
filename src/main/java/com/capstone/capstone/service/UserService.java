package com.capstone.capstone.service;

import com.capstone.capstone.dto.*;
import com.capstone.capstone.entity.UserEntity;
import com.capstone.capstone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Map<String,Object> signUp(UserDTO userDTO) { // 유저 회원가입
        Map<String, Object> result = new HashMap<>();
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDTO.getUserId());
        Optional<UserEntity> byUserName = userRepository.findByUserName(userDTO.getUserName());

        if(byUserId.isPresent()){//같은 userId 있을 경우
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SAME_USER_ID);
            result.put("code",commonCodeDTO);
            return result;
        }
        if(byUserName.isPresent()){//같은 userName 있을 경우
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SAME_USER_NAME);
            result.put("code",commonCodeDTO);
            return result;
        }
        // 1. dto -> entity 변환
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
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
        // 1. userId로 DB조회
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDTO.getUserId());

        // 2. DB에 저장된 password와 일치하는지 판단
        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            if(userEntity.getUserPassword().equals(userDTO.getUserPassword())){ // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(userEntity);
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_SIGN_IN);
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

    @Transactional // DB update
    public Map<String,Object> passwordChange(UserPasswordChangeDTO userPasswordChangeDTO){
        Map<String, Object> result = new HashMap<>();
        Optional<UserEntity> byUserId = userRepository.findByUserId(userPasswordChangeDTO.getUserId());

        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            if(userEntity.getUserPassword().equals(userPasswordChangeDTO.getUserPassword())){ // 비밀번호 일치
                // userPassword를 newUserPassword로 DB업데이트
                userEntity.setUserPassword(userPasswordChangeDTO.getNewUserPassword());
                // entity -> dto 변환 후 리턴
                UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(userEntity);
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_PASSWORD_CHANGE);
                result.put("data",userResponseDTO);
                result.put("code",commonCodeDTO);
                return result;
            }
            else{ // 비밀번호 불일치
                return null;
            }
        }
        else {// 조회 결과 X
            return null;
        }
    }

    public Map<String,Object> deleteUser(UserDeleteDTO userDeleteDTO) {
        Map<String, Object> result = new HashMap<>();
        // 1. userId로 DB조회
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDeleteDTO.getUserId());

        // 2. DB에 저장된 password와 일치하는지 판단
        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            if(userEntity.getUserPassword().equals(userDeleteDTO.getUserPassword())){ // 비밀번호 일치
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
