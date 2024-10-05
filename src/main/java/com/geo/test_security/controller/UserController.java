package com.geo.test_security.controller;

import com.geo.test_security.controller.dto.CreateUserDto;
import com.geo.test_security.entities.Role;
import com.geo.test_security.entities.User;
import com.geo.test_security.repository.RoleRepository;
import com.geo.test_security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceAlreadyExistsException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Set;

@RestController

public class UserController {


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto userDto){
        var roleBasic = roleRepository.findByName(Role.Values.BASIC);
        System.out.println(userDto.username() + " " + userDto.password());
        var userFromDb = userRepository.findByUsername(userDto.username());

        userFromDb.ifPresentOrElse(user -> {
           throw  new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }, () -> {
            var user = new User();
            user.setPassword(bCryptPasswordEncoder.encode(userDto.password()));
            user.setUsername(userDto.username());
            roleBasic.ifPresent( role -> user.setRole(Set.of(role)));
            userRepository.save(user);
        });

        return new ResponseEntity<>("Usuário criado com sucesso",HttpStatus.CREATED);

    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listAllUsers(){
        var listaUsers = userRepository.findAll();
        return new ResponseEntity<>(listaUsers,HttpStatus.OK);
    }



}
