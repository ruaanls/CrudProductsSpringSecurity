package br.com.fiap.productsecurity.service;

import br.com.fiap.productsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService
// Especialmente para a classse Authorization Service ela deve implementar essa classe do spring security
// A classe AuthorizationService Implementando a UserDetailsService significa que em toda autenticação ele será chamado automaticamente no fluxo do spring security
// Incluindo o único e principal método o loadUserByUsername que vai carregar o usuário do banco de dados

{

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Aqui entendemos o por que aquele findByUsername tem que devolver UserDetails ao invés de User
        // Por que esse método espera que seja retornado um objeto do tipo UserDetails
        return userRepository.findByLogin(login);
    }
}
