package com.capstone.capstone.service;

import com.capstone.capstone.dto.UserDTO;
import com.capstone.capstone.dto.UserPasswordChangeDTO;
import com.capstone.capstone.dto.UserResponseDTO;
import com.capstone.capstone.entity.UserEntity;
import com.capstone.capstone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO signUp(UserDTO userDTO) { // 유저 회원가입
        // 1. dto -> entity 변환
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        // 2. repository의 save 호출
        UserEntity save = userRepository.save(userEntity);// entity 객체를 넘겨줘야 함

        UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(save);

        return  userResponseDTO;
    }


    public UserResponseDTO signIn(UserDTO userDTO) {
        // 1. userId로 DB조회
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDTO.getUserId());

        // 2. DB에 저장된 password와 일치하는지 판단
        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            if(userEntity.getUserPassword().equals(userDTO.getUserPassword())){ // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(userEntity);
                return userResponseDTO;
            }
            else{ // 비밀번호 불일치
                return null;
            }
        }
        else {// 조회 결과 X
            return null;
        }
    }

    @Transactional // DB update
    public UserResponseDTO passwordChange(UserPasswordChangeDTO userPasswordChangeDTO){
        Optional<UserEntity> byUserId = userRepository.findByUserId(userPasswordChangeDTO.getUserId());

        if(byUserId.isPresent()){// 조회 결과 O
            UserEntity userEntity = byUserId.get();
            if(userEntity.getUserPassword().equals(userPasswordChangeDTO.getUserPassword())){ // 비밀번호 일치
                // userPassword를 newUserPassword로 DB업데이트
                userEntity.setUserPassword(userPasswordChangeDTO.getNewUserPassword());
                // entity -> dto 변환 후 리턴
                UserResponseDTO userResponseDTO = UserResponseDTO.toUserResponseDTO(userEntity);
                return userResponseDTO;
            }
            else{ // 비밀번호 불일치
                return null;
            }
        }
        else {// 조회 결과 X
            return null;
        }
    }
}
