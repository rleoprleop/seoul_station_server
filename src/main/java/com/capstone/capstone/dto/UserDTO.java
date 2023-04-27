package com.capstone.capstone.dto;

import com.capstone.capstone.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String userId;
    private String userPassword;
    private String nickName;

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = UserDTO.builder()
                .userId(userEntity.getUserId())
                .nickName(userEntity.getNickName())
                .userPassword(userEntity.getUserPassword())
                .build();
        /*UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setUserName(userEntity.getUserName());*/
        return userDTO;
    }
}
