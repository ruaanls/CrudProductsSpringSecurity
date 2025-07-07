package br.com.fiap.productsecurity.Exception;

import br.com.fiap.productsecurity.Exception.Custom.OracleInputException;
import br.com.fiap.productsecurity.Exception.Message.LoginInvalid;
import br.com.fiap.productsecurity.Exception.Message.RegisterFailed;
import org.springframework.http.HttpStatus;
import br.com.fiap.productsecurity.Exception.Message.OracleInput;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(br.com.fiap.productsecurity.Exception.Custom.RegisterFailedException.class)
    private ResponseEntity<RegisterFailed> registerFailedHandler(br.com.fiap.productsecurity.Exception.Custom.RegisterFailedException exception)
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
