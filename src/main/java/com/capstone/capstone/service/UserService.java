package com.capstone.capstone.service;

import com.capstone.capstone.VO.UserRolesVO;
import com.capstone.capstone.VO.UserVO;
import com.capstone.capstone.dto.*;
//import com.capstone.capstone.entity.UserEntity;
//import com.capstone.capstone.repository.UserRepository;
//import com.capstone.capstone.security.JwtTokenProvider;
//import jakarta.transaction.Transactional;
import com.capstone.capstone.mapper.PlayerMapper;
import com.capstone.capstone.mapper.UserMapper;
import com.capstone.capstone.parameter.UpdateUserPasswordParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    //private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PlayerMapper playerMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder passwordEncoder;

    public Map<String,Object> signUp(UserDTO userDTO) { // 유저 회원가입
        Map<String, Object> result = new HashMap<>();
        if(userDTO.getUserId().trim().isEmpty() || userDTO.getUserPassword().trim().isEmpty()){
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.NO_DATA);
            result.put("code",commonCodeDTO);
            return result;
        }
        Optional<UserVO> byUserId = userMapper.getUserByUserId(userDTO.getUserId());
        Optional<UserVO> byNickName = userMapper.getUserByNickName(userDTO.getNickName());


        if(byUserId.isPresent()){//같은 userId 있을 경우
            System.out.println(byUserId.get().getUserId());
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SAME_USER_ID);
            result.put("code",commonCodeDTO);
            return result;
        }
        if(byNickName.isPresent()){//같은 nickName 있을 경우
            CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SAME_USER_NAME);
            result.put("code",commonCodeDTO);
            return result;
        }
        UserVO userVO = UserVO.builder()
                .userId(userDTO.getUserId())
                .userPassword(passwordEncoder.encode(userDTO.getUserPassword()))
                .nickName(userDTO.getNickName())
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        userMapper.createUser(userVO);
        Long id = userVO.getId();
        System.out.println(id);
        UserRolesVO userRolesVO = UserRolesVO.builder()
                .userEntityId(id)
                .roles("USER")
                .build();

        userMapper.createUserRole(userRolesVO);
        playerMapper.createPlayer(userVO);
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_SIGN_UP);
        result.put("code",commonCodeDTO);
        return  result;
    }

    /*public Map<String,Object> signIn(UserDTO userDTO) {
        Map<String, Object> result = new HashMap<>();

        result.put("ddd","ddd");
        return result;
    }*/

    public Map<String,Object> passwordChange(UserPasswordChangeDTO userPasswordChangeDTO){
        Map<String, Object> result = new HashMap<>();
        Optional<UserVO> byUserId = userMapper.getUserByUserId(userPasswordChangeDTO.getUserId());

        if(byUserId.isPresent()){// 조회 결과 O
            UserVO userVO = byUserId.get();
            if(passwordEncoder.matches(userPasswordChangeDTO.getUserPassword(),userVO.getUserPassword())){ // 비밀번호 일치
                // userPassword를 newUserPassword로 DB업데이트
                UpdateUserPasswordParam updateUserPasswordParam = UpdateUserPasswordParam.builder()
                        .id(userVO.getId())
                        .modifiedDate(LocalDateTime.now())
                        .userPassword(passwordEncoder.encode(userPasswordChangeDTO.getNewUserPassword()))
                        .build();

                userMapper.updateUserPassword(updateUserPasswordParam);
                // entity -> dto 변환 후 리턴
                CommonCodeDTO commonCodeDTO = CommonCodeDTO.toCommonCodeDTO(CommonCode.SUCCESS_PASSWORD_CHANGE);
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
        Optional<UserVO> byUserId = userMapper.getUserByUserId(userDeleteDTO.getUserId());

        // 2. DB에 저장된 password와 일치하는지 판단
        if(byUserId.isPresent()){// 조회 결과 O
            UserVO userVO = byUserId.get();
            System.out.println(passwordEncoder.encode(userVO.getUserPassword())+" && "+userDeleteDTO.getUserPassword());
            if(passwordEncoder.matches(userDeleteDTO.getUserPassword(),userVO.getUserPassword())){ // 비밀번호 일치
                System.out.println(passwordEncoder.encode(userVO.getUserPassword())+" && "+userDeleteDTO.getUserPassword());

                playerMapper.deletePlayer(userVO.getId());
                userMapper.deleteUserRole(userVO.getId());//delete
                userMapper.deleteUser(userVO.getId());
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
