package br.com.fiap.productsecurity.config;

import br.com.fiap.productsecurity.repository.UserRepository;
import br.com.fiap.productsecurity.service.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter

{
    // Extensão OBRIGATÓRIA da classe de filtros personalizados do Spring Security
    // OncePerRequestFilter = Essa classe vai ser filha e vai ter acesso aos métodos que permitem o filtro de 1 vez por requisição
    // A cada requisição não importa qual ela seja o Spring security irá jogar os dados dela para o método doFilterInternal e fazer a lógica que está lá

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       var token = this.recoverToken(request);
       if(token != null)
       {
           try
           {
               // Validar o token JWT e retornar o login do usuário
               var login = tokenService.validateToken(token);
               // Com o login do usuário procurar no banco de dados e receber um user do tipo UserDetails
               UserDetails user = userRepository.findByLogin(login);

               // Autenticar o usuário com a classe especial do spring security UsernamePasswordAuthenticationToken, os parametros são sempre dessa forma
               // Ela serve para autenticar o usuário e saber quais roles ele tem acesso
               var authentication = new UsernamePasswordAuthenticationToken(user, null , user.getAuthorities());
               // Salvando o token da autenticação no contexto do spring
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
           catch (TokenExpiredException e) {
               // Token expirado - será capturado pelo AuthenticationEntryPoint
               request.setAttribute("exception", e);
           } catch (JWTVerificationException e) {
               // Token inválido - será capturado pelo AuthenticationEntryPoint
               System.out.println("JWTVerificationException capturada: " + e.getMessage());
               request.setAttribute("exception", e);
           } catch (Exception e) {
               // Outros erros de token
               request.setAttribute("exception", e);
           }
       }
       //Manda para o próximo filtro (Filtro padrão do spring security -- Classe Spring configuration)
       filterChain.doFilter(request, response);
    }

    // Método para pegar o token dos headers das requisições
    // Apagar o Bearer da string e deixar só o token para utilizar nas lógicas
    private String recoverToken(HttpServletRequest request)
    {
        var token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer "))
        {
            return null;
        }
        return token.replace("Bearer ", "");
    }
}
