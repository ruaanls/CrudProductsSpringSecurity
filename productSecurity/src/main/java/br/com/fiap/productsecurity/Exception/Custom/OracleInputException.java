package br.com.fiap.productsecurity.Exception.Custom;

import org.springframework.http.HttpStatus;

public class OracleInputException extends RuntimeException {
    public OracleInputException(String message) {
        super(message);
    }

    public OracleInputException(String message, Throwable cause) {
        super(message, cause);
    }


    public OracleInputException() {
        super("Erro ao inserir dados no banco de dados, favor tente novamente mais tarde");
    }


}
