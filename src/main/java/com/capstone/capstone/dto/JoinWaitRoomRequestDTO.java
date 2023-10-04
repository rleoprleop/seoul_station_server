package com.capstone.capstone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinWaitRoomRequestDTO {
    private String msg;//유저 아이디
    //private String nickName;
    //private int limitMembers;//같이 할 사람 수. 2 or 4 예상
}
