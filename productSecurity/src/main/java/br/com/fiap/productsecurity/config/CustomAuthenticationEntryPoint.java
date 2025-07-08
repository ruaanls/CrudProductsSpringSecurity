package br.com.fiap.productsecurity.config;

import br.com.fiap.productsecurity.Exception.Message.AuthInvalid;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        AuthInvalid error;

        Exception storedJwtException = (Exception) request.getAttribute("exception");

        if (storedJwtException instanceof TokenExpiredException ) {
            // Token JWT expirado
            error = new AuthInvalid(HttpStatus.UNAUTHORIZED, "Token JWT expirado, por favor realize o login novamente!");

        } else if (storedJwtException instanceof JWTVerificationException) {
            // Token JWT inválido
            error = new AuthInvalid(HttpStatus.UNAUTHORIZED, "Token JWT inválido, por favor realize o login novamente!");

        } else if (authException instanceof BadCredentialsException) {
            // Login e senha incorretos
            error = new AuthInvalid(HttpStatus.UNAUTHORIZED, "Login ou senha Incorretos, por favor tente novamente");

        }
        else if (authException instanceof InternalAuthenticationServiceException) {
            error = new AuthInvalid(HttpStatus.UNAUTHORIZED, "Usuário não existe no banco de dados, por favor realize o cadastro primeiro!");
        }

        else {
            // Outras exceções de autenticação
            error = new AuthInvalid(HttpStatus.UNAUTHORIZED, "Erro de autenticação: " + authException.getMessage());
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(error);
        response.getWriter().write(jsonResponse);
    }


}
