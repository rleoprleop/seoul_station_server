package com.capstone.capstone.controller;

import com.capstone.capstone.dto.UserDTO;
import com.capstone.capstone.dto.UserDeleteDTO;
import com.capstone.capstone.dto.UserPasswordChangeDTO;
import com.capstone.capstone.dto.UserResponseDTO;
import com.capstone.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController //rest api 처리하는 controller
@RequiredArgsConstructor // 자동 생성자 주입
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<Map<String,Object>> signUp(final @RequestBody UserDTO userDTO) {
        // @ModelAttribute: html의 form태그로 오는 x-www-form-urlencoded 데이터 받기 가능,
        // @RequestBody: html의 form태그로 오는 x-www-form-urlencoded 데이터 받기 불가능, json형태로 받는게 좋음.
        System.out.println(userDTO);
        Map<String, Object> result = userService.signUp(userDTO);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String,Object>> signIn(final @RequestBody UserDTO userDTO) {
        Map<String, Object> result = userService.signIn(userDTO);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/password-change")
    public ResponseEntity<Map<String,Object>> passwordChange(final @RequestBody UserPasswordChangeDTO userPasswordChangeDTO) {
        Map<String, Object> result = userService.passwordChange(userPasswordChangeDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<Map<String,Object>> deleteUser(final @RequestBody UserDeleteDTO userDeleteDTO) {
        Map<String, Object> result = userService.deleteUser(userDeleteDTO);
        return ResponseEntity.ok().body(result);
    }
}
