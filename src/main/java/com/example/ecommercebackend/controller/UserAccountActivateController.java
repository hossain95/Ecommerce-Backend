package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.service.UserAccountActivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/account")
public class UserAccountActivateController {
    @Autowired
    private UserAccountActivateService userAccountActivateService;

    @RequestMapping("/activate/{emailId}/{code}")
    public ResponseEntity<CommonResponse> accountActivate(@RequestBody @PathVariable("emailId") String emailId, @PathVariable("code") String code){
        return userAccountActivateService.accountActivate(emailId, code);
    }
}