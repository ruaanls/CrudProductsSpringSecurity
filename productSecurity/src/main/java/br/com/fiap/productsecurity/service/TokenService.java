package br.com.fiap.productsecurity.service;

import br.com.fiap.productsecurity.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService
{
    @Value( "${api.security.token.secret}")
    private String secret;
    public String generateToken(User user)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("apisecurity")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        }
        catch (JWTCreationException e)
        {
            throw new RuntimeException("Erro ao gerar token: ", e.getCause());
        }

    }

    public String validateToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("apisecurity")
                    .build()
                    .verify(token)
                    .getSubject();

        }
        catch (TokenExpiredException e)
        {
            throw new TokenExpiredException("Token JWT Expirado por favor, realize um login novamente", e.getExpiredOn());
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token JWT inválido por favor, realize um login novamente ", e.getCause());
        }
    }


    private Instant genExpirationDate()
    {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.systemDefault().getRules().getOffset(Instant.now()));

    }
}
