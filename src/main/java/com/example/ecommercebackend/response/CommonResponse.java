package com.example.ecommercebackend.response;

public class CommonResponse {
    private String message;
    private Enum status;

    public CommonResponse(String message, Enum status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }
}
