package br.com.fiap.productsecurity.Exception.Message;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


public class OracleInput
{
    private HttpStatus status;
    private String message;

    public OracleInput(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
