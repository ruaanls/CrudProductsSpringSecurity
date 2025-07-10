package br.com.fiap.productsecurity.Controller;

import br.com.fiap.productsecurity.DTO.AuthenticationDTO;
import br.com.fiap.productsecurity.DTO.LoginResponseDTO;
import br.com.fiap.productsecurity.DTO.RegisterDTO;
import br.com.fiap.productsecurity.Exception.Custom.RegisterFailedException;
import br.com.fiap.productsecurity.model.User;
import br.com.fiap.productsecurity.repository.UserRepository;
import br.com.fiap.productsecurity.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data)
    {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
            var auth = authManager.authenticate(usernamePassword);
            var token = this.tokenService.generateToken((User) auth.getPrincipal());
            LoginResponseDTO loginResponse = new LoginResponseDTO();
            loginResponse.setToken(token);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data)
    {
        if(this.userRepository.findByLogin(data.getLogin()) != null)
        {
            // Se existir algum usuário retornar badrequest
            // NULL = Não existe usuário pronto para registrar
            throw new RegisterFailedException();
        }
        // Criptografar a senha
        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.getPassword());
        // Usuário salvo no banco de dados com a senha criptografada
        User newUser = new User(data.getLogin(), senhaCriptografada, data.getUser_role());
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }





}
