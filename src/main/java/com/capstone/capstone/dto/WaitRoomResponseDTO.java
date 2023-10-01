package com.capstone.capstone.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WaitRoomResponseDTO {
    private String userId;
    private Long waiting;

}
