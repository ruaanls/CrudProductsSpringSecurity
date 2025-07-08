package br.com.fiap.productsecurity.Exception;

import br.com.fiap.productsecurity.Exception.Custom.OracleInputException;
import br.com.fiap.productsecurity.Exception.Custom.RegisterFailedException;
import br.com.fiap.productsecurity.Exception.Message.RegisterFailed;
import org.springframework.http.HttpStatus;
import br.com.fiap.productsecurity.Exception.Message.OracleInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(RegisterFailedException.class)
    private ResponseEntity<RegisterFailed> registerFailedHandler(RegisterFailedException exception)
    {
        RegisterFailed Exception = new RegisterFailed(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Exception);
    }

    @ExceptionHandler(OracleInputException.class)
    private ResponseEntity<OracleInput> bdInputFailedHandler(OracleInputException exception)
    {
        OracleInput Exepction = new OracleInput(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Exepction);
    }


}
