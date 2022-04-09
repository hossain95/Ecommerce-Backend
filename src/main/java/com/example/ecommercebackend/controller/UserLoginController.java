package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.UserDto;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.LoginResponse;
import com.example.ecommercebackend.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;


    @PostMapping("/login")
    public ResponseEntity<CommonResponse> userLogin(@RequestBody UserDto userDto){
        return userLoginService.userLogin(userDto);
    }
}