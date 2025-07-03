package br.com.fiap.productsecurity.repository;

import br.com.fiap.productsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // O método de FIND usuários é usado por um outro método do spring security no authorization Service
    // Esse método obriga e espera receber um usuário do tipo USER DETAILS
    UserDetails findByUsername(String username);
}
