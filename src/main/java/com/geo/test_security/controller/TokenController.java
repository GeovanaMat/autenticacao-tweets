package com.geo.test_security.controller;

import com.geo.test_security.controller.dto.LoginRequest;
import com.geo.test_security.controller.dto.LoginResponse;
import com.geo.test_security.entities.Role;
import com.geo.test_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtEncoder jwtEncoder;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository , BCryptPasswordEncoder bCryptPasswordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder1) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
            var user = userRepository.findByUsername(loginRequest.username());
            if(user.isEmpty() || !user.get().isLogginCorrect(loginRequest,bCryptPasswordEncoder)) {
                throw  new BadCredentialsException("Usuário ou senha inválido");
            }

            var now = Instant.now();
            var expireIn = 300L;
            String scope = user.get().getRole().stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.joining(" "));


            var claims = JwtClaimsSet.builder()
                    .issuer("testSecurityBackend")
                    .subject(user.get().getId().toString())
                    .expiresAt(now.plusSeconds(expireIn))
                    .claim("scope", scope)
                    .build();

            var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return new ResponseEntity<LoginResponse>(new LoginResponse(jwtValue,expireIn),HttpStatus.OK);

    }
}
