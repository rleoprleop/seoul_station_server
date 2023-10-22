package com.capstone.capstone.parameter;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlayerHealthStageParam {
    private Long id;
    private int health;
    private String stage;
}
