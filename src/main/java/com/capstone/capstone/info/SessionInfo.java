package com.capstone.capstone.info;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SessionInfo {
    private String userId;
    private String roomId;
    private String sessionId;
}
