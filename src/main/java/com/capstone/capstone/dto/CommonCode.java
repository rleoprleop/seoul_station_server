package com.capstone.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public enum CommonCode {
    NOT_FOUND_USER_ID(0400,"not found user Id"),
    SAME_USER_ID(0401,"same user Id"),
    SAME_USER_NAME(0402,"same user name"),
    ERROR_PASSWORD(0403,"error password");

    private int status;
    private String message;
}
