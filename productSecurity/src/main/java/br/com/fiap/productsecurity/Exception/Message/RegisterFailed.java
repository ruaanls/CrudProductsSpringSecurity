package br.com.fiap.productsecurity.Exception.Message;

import org.springframework.http.HttpStatus;

public class RegisterFailed
{
    private HttpStatus status;
    private String message;


    public RegisterFailed(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
