package com.capstone.capstone.controller;

import com.capstone.capstone.dto.UserDTO;
import com.capstone.capstone.dto.UserPasswordChangeDTO;
import com.capstone.capstone.dto.UserResponseDTO;
import com.capstone.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //rest api 처리하는 controller
@RequiredArgsConstructor // 자동 생성자 주입
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> signUp(final @ModelAttribute UserDTO userDTO) {
        // @ModelAttribute: html의 form태그로 오는 x-www-form-urlencoded 데이터 받기 가능,
        // @RequestBody: html의 form태그로 오는 x-www-form-urlencoded 데이터 받기 불가능, json형태로 받는게 좋음.
        System.out.println(userDTO);
        UserResponseDTO userResponseDTO = userService.signUp(userDTO);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDTO> signIn(final @ModelAttribute UserDTO userDTO) {
        UserResponseDTO signInResult = userService.signIn(userDTO);
        if(signInResult != null) {
            return ResponseEntity.ok(signInResult);
        }//성공
        else {
            return null;
        }//실패
    }

    @PostMapping("/password-change")
    public ResponseEntity<UserResponseDTO> passwordChange(final @ModelAttribute UserPasswordChangeDTO userPasswordChangeDTO) {
        UserResponseDTO signInResult = userService.passwordChange(userPasswordChangeDTO);
        if(signInResult != null) {
            return ResponseEntity.ok(signInResult);
        }//성공
        else {
            return null;
        }//실패
    }
}
