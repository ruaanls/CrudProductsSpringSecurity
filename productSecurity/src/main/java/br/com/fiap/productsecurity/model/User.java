package br.com.fiap.productsecurity.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
public class User implements UserDetails // Implementação OBRIGATÓRIA para a classe user, métodos dela são chamados no doFilter mais a frente para autenticação
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Utilizamos UUID para maior segurança, mas não tem problema fazer com long
    private String id;
    private String login;
    private String password;
    private UserRole user_role;

    public User(String login, String senhaCriptografada, UserRole userRole)
    {
        this.login = login;
        this.password = senhaCriptografada;
        this.user_role = userRole;
    }

    public User() {

    }

    @Override
    // Sempre quando o usuário for se logar, esse método vai verificar quais são as ROLES dele (Admin ou User)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.user_role.equals(UserRole.ADMIN))
        {
            // Se a role do usuário for ADMIN o método irá retornar uma lista com as duas authorities especiais do spring
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        else
        {
            // Se a role do usuário for user o método irá retornar uma lista com apenas uma authority especial do spring (ROLE_USER)
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public String getLogin() {
        return this.login;
    }

    public String getId() {
        return id;
    }

    public UserRole getUser_role() {
        return user_role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
