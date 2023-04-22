package com.capstone.capstone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDeleteDTO {
    private String userId;
    private String userPassword;

}
