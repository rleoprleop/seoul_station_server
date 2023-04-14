package com.capstone.capstone.dto;

import com.capstone.capstone.Test.BizExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommonCodeDTO {
    private int status;
    private String message;

    public static CommonCodeDTO toCommonCodeDTO(CommonCode CommonCode){
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.builder()
                .status(CommonCode.getStatus())
                .message(CommonCode.getMessage()).build();
        return commonCodeDTO;
    }
}