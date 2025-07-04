package br.com.fiap.productsecurity.DTO;

import br.com.fiap.productsecurity.model.UserRole;

public record RegisterDTO(String login, String password, UserRole user_role) {
}
