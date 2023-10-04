package com.capstone.capstone.VO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageJoinMobVO {
    private int x;
    private int y;
    private int maxHealth;
    private String mobName;
    private String stageName;
}
