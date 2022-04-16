package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Tag(name = "Check Out")
public class CheckOutController {
    @Autowired
    private CheckOutService checkOutService;

    @PostMapping("/user/{userId}/checkout")
    public CommonResponse checkOut(@PathVariable("userId") Long userId){
        return checkOutService.orderCheckOut(userId);
    }
}
