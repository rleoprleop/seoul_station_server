package com.capstone.capstone.VO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRolesVO {
    private Long userEntityId;
    private String roles;
}
