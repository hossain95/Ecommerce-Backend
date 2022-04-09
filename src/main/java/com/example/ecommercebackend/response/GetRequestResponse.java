package com.example.ecommercebackend.response;

import java.util.List;

public class GetRequestResponse<T>{
    private Enum status;
    private List<T> results;

    public GetRequestResponse(Enum status, List<T> results) {
        this.status = status;
        this.results = results;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
