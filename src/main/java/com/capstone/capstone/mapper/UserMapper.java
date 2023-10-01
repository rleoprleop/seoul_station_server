package com.capstone.capstone.mapper;

import com.capstone.capstone.VO.UserJoinRolesVO;
import com.capstone.capstone.VO.UserRolesVO;
import com.capstone.capstone.VO.UserVO;
import com.capstone.capstone.dto.UserDTO;
import com.capstone.capstone.parameter.UpdateUserPasswordParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    UserVO getUserById(int id);

    Optional<UserVO> getUserByUserId(String userId);

    Optional<UserVO> getUserByNickName(String nickName);

    Optional<UserJoinRolesVO> getUserJoinRoles(String userId);


    List<UserVO> getUserList();

    Long createUser(UserVO userVO);

    Long createUserRole(UserRolesVO userRolesVO);

    Long updateUser(UserVO userVO);

    Long updateUserPassword(UpdateUserPasswordParam updateUserPasswordParam);

    Long deleteUser(Long id);
    Long deleteUserRole(Long id);
}
