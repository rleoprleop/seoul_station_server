package com.capstone.capstone.dto;

//import com.capstone.capstone.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordChangeDTO {
    private String userId;
    private String userPassword;
    private String newUserPassword;

}
