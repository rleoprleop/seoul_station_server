package com.capstone.capstone.Test;

import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Builder
public class TestCommonResponse {
    private Enum code;
    public static ResponseEntity<?> ok(TestResponse testResponse){
        return ResponseEntity.ok(testResponse);
    }
}
