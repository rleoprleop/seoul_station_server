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
    SUCCESS_DELETE(1203,"success DELETE"),
    NOT_FOUND_USER_ID(1400,"not found user Id"),
    SAME_USER_ID(1401,"same user Id"),
    SAME_USER_NAME(1402,"same user name"),
    ERROR_PASSWORD(1403,"error password"),
    FAIL_AUTHENTICATION(1404,"fail authentication"),//인증 실패
    TOKEN_EXPIRED(1405,"token expired"),//토큰이 만료됨.
    NOT_FOUND_TOKEN(1407,"not found token"),//토큰이 없음.

    TOKEN_INVALID(1406,"token invalid");//토큰이 유효하지 않음

    private int status;
    private String message;
}
