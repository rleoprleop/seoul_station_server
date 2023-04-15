package com.capstone.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public enum CommonCode {
    SUCCESS_SIGN_IN(1200,"success sign in"),
    SUCCESS_SIGN_UP(1201,"success sign up"),
    SUCCESS_PASSWORD_CHANGE(1202,"success password change"),
    NOT_FOUND_USER_ID(1400,"not found user Id"),
    SAME_USER_ID(1401,"same user Id"),
    SAME_USER_NAME(1402,"same user name"),
    ERROR_PASSWORD(1403,"error password");

    private int status;
    private String message;
}
