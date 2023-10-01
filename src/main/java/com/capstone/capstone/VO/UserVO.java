package com.capstone.capstone.VO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    private Long id;
    private String userId;
    private String userPassword;
    private String nickName;
    private LocalDateTime modifiedDate;
    private LocalDateTime createDate;
}
