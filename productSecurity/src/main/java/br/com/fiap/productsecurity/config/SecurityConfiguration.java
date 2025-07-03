package br.com.fiap.productsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Definição de classe de configuração
@EnableWebSecurity // Habilitar as configurações personalizadas do spring security
public class SecurityConfiguration {

    @Bean
    // Configurações do filtro de segurança PADRÃO do spring security
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception

    {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                // Gestão de dados da sessão definido para stateless
                // Stateless = Não guardar os dados de autenticação/IP e afins do usuário na sessão ou servidor (Melhor prática)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Configuração de permissões específicas das apis
                .authorizeHttpRequests(authorize -> authorize
                        // api de login e registro todos podem acessar
                        .requestMatchers(HttpMethod.POST,"auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "auth/register").permitAll()
                        // Api de cadastro, deletar e atualizar somente administradores podem acessar
                        .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/products").hasRole("ADMIN")
                        // Qualquer outra api apenas autenticados podem acessar
                        .anyRequest().authenticated()
                )
                .build();
    }


    // Método que garante a injeção de dependencias e EVITA O BUG DO AUTENTICATE lá no controller quando for comparar os hashs
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // método para criptografar e descriptografar senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
