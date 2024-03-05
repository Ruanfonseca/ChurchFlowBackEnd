package com.system.churchflow.controllers;


import com.system.churchflow.dto.*;
import com.system.churchflow.infra.security.TokenService;
import com.system.churchflow.model.User;
import com.system.churchflow.repository.UserRepository;
import com.system.churchflow.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class LoginController {

    @Autowired
   private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService service;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        User user = service.findUser(data.login());

        System.out.println(new LoginResponseDTO(token,user.getName(),user.getLogin(),user.getRole(), user.getPicture()));
        return ResponseEntity.ok(new LoginResponseDTO(token,user.getName(),user.getLogin(),user.getRole(),user.getPicture()));

    }

    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody @Valid RegisterDTO registerDTO){

        //ja existe alguem cadastrado com esse email
       if(this.service.findByLogin(registerDTO.login()) != null){
           Message message = new Message("Usuário já cadastrado");

           return ResponseEntity.badRequest().body(message);
       }

        System.out.println(registerDTO);

       String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
       User newUser = new User(registerDTO.login(),encryptedPassword,registerDTO.name(),registerDTO.role());

       this.service.save(newUser);
        Message message = new Message("Cadastro realizado com sucesso");
       return ResponseEntity.ok().body(message);
    }


    @PostMapping("/validUser")
    public ResponseEntity<?> validUser(ValidUserDTO dto){
        //não existe
        if(this.service.findByLogin(dto.login()) != null){
            System.out.println("Erro");
            return ResponseEntity.badRequest().build();
        }else {
            User user = service.findUser(dto.login());
            return ResponseEntity.ok().body(user);
        }
    }


    @PostMapping("/recuperationSecundary")
    public ResponseEntity<?> recuperationPassword(@RequestBody @Valid RecuperationDTO recuperationDTO ){
        /*Será preciso refatorar esse código pois esta implementação não é a melhor*/

        var usernamePassword = new UsernamePasswordAuthenticationToken(recuperationDTO.login(), recuperationDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        User userToEdit = this.service.findUser(recuperationDTO.login());
        System.out.println(userToEdit);
        String encryptedPassword = new BCryptPasswordEncoder().encode(recuperationDTO.password());
        userToEdit.setPassword(encryptedPassword);
        userToEdit.setTokenInUse(token);

        service.save(userToEdit);
      return ResponseEntity.ok().body("Feito");
    }




}
