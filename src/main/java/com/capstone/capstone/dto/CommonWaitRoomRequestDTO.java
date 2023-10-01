package com.capstone.capstone.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonWaitRoomRequestDTO {
    private String userId;
    private String nickName;
    private String waitRoomId;
    private String state;//join, wait, leave
}
