package com.capstone.capstone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageGetRequestDTO {
    private String userId;
    private Long id;
}
