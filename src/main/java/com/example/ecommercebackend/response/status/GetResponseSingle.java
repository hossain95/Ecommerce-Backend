package com.example.ecommercebackend.response.status;

import org.springframework.http.HttpStatus;

import java.util.List;

public class GetResponseSingle<T> {
    private HttpStatus status;
    private T results;

    public GetResponseSingle(HttpStatus status, T results) {
        this.status = status;
        this.results = results;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
