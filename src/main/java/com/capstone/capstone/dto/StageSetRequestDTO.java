package com.capstone.capstone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageSetRequestDTO {
    private Long id;
    private int health;
    private int stage;
}
