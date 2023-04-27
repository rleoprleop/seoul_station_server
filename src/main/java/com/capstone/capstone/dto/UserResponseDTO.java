package com.capstone.capstone.dto;

import com.capstone.capstone.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class UserResponseDTO{
    private Long id;
    private String userName;

    private LocalDateTime userCreateDate;

    public static UserResponseDTO toUserResponseDTO(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(userEntity.getId())
                .userName(userEntity.getNickName())
                .userCreateDate(userEntity.getCreateDate())
                .build();
        return userResponseDTO;
    }
}
