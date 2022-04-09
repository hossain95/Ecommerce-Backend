package com.example.ecommercebackend.security;

import net.bytebuddy.utility.RandomString;

public class VerificationCode {
    public String GenerateVerificationCode(){
        String code = RandomString.make(6);
        return code;
    }
}
