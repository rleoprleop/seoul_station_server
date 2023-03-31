package com.capstone.capstone.controller;

import com.capstone.capstone.dto.UserDTO;
import com.capstone.capstone.entity.UserEntity;
import com.capstone.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController //rest api 처리하는 controller
@RequiredArgsConstructor // 자동 생성자 주입
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public void signUp(final @ModelAttribute UserDTO userDTO) {
        // @ModelAttribute: html의 form태그로 오는 x-www-form-urlencoded 데이터 받기 가능,
        // @RequestBody: html의 form태그로 오는 x-www-form-urlencoded 데이터 받기 불가능, json형태로 받는게 좋음.
        System.out.println(userDTO);
        userService.save(userDTO);
    }

    @PostMapping("/sign-in")
    public void signIn(final @ModelAttribute UserDTO userDTO) {
        UserDTO signInResult = userService.signIn(userDTO);
        if(signInResult != null) {

        }//성공
        else {

        }//실패
    }
}
