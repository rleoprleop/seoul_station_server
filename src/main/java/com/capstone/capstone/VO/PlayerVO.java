package com.capstone.capstone.VO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerVO {
    private Long playerId;
    private String health;
    private String stage;
}
