package com.capstone.capstone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageRequestDTO {
    private String userId;
    private Long stageId;
}
