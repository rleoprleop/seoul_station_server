package com.capstone.capstone.VO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageJoinBackgroundVO {
    private String stageName;
    private int backgroundCanvasLength;
    private int backgroundLength;
    private String backgroundName;
}
