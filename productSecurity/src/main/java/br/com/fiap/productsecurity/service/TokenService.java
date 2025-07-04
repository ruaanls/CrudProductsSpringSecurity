package br.com.fiap.productsecurity.service;

import br.com.fiap.productsecurity.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
            throw new JWTCreationException("Erro ao gerar token: ", e.getCause());
        }

    }

    public String validateToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm)
                    .withIssuer("apisecurity")
                    .build()
                    .verify(token)
                    .getSubject();
            return token;
        }
        catch (JWTVerificationException e)
        {
            throw new JWTVerificationException("Erro ao validar token:", e.getCause());
        }
    }


    private Instant genExpirationDate()
    {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of( "+03:00"));
    }
}
