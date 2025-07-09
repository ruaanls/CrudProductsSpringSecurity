package br.com.fiap.productsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthenticationDTO {
    @NotBlank(message = "O campo login não deve ficar em branco! ")
    @NotNull(message = "O campo login é obrigatório")
    private String login;
    @NotBlank(message = "O campo password não deve ficar em branco! ")
    @NotNull(message = "O campo password é obrigatório")
    private String password;

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
}
