package com.capstone.capstone.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TestCommonCode {
    private int status;
    private String code;
    private String message;

    public TestCommonCode(BizExceptionCode bizExceptionCode){
        this.status=bizExceptionCode.getStatus();
        this.code=bizExceptionCode.getCode();
        this.message=bizExceptionCode.getMessage();
    }
}
