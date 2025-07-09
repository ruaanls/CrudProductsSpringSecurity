package br.com.fiap.productsecurity.DTO;

import br.com.fiap.productsecurity.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterDTO
{
    @NotBlank(message = "O campo login não deve ficar em branco! ")
    @NotNull(message = "O campo login é obrigatório")
    private String login;
    @NotBlank(message = "O campo password não deve ficar em branco! ")
    @NotNull(message = "O campo password é obrigatório")
    private String password;
    private UserRole user_role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUser_role() {
        return user_role;
    }

    public void setUser_role(UserRole user_role) {
        this.user_role = user_role;
    }
}
