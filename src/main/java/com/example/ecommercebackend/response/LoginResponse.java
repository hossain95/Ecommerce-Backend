package com.example.ecommercebackend.response;

public class LoginResponse {
    private String token;
    private Enum status;

    public LoginResponse(String token, Enum status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }
}
