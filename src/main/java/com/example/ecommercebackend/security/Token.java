package com.example.ecommercebackend.security;

import net.bytebuddy.utility.RandomString;

public class Token {
    public String getToken(){
        String token = RandomString.make(64);
        return token;
    }
}
