package com.dev.api_kls.controller;

import com.dev.api_kls.model.User;
import com.dev.api_kls.model.dtos.LoginUserDto;
import com.dev.api_kls.model.dtos.RegisterUserDto;
import com.dev.api_kls.model.response.LoginResponse;
import com.dev.api_kls.repository.UserRepository;
import com.dev.api_kls.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        try {
            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            var token = jwtService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto registerUserDTO) {
        if (userRepository.findByEmail(registerUserDTO.getEmail()) != null)
            return ResponseEntity.badRequest().build();

        String encryptPassword = passwordEncoder.encode(registerUserDTO.getPassword());
        User newUser = new User(
                registerUserDTO.getEmail(),
                registerUserDTO.getName(),
                registerUserDTO.getUsername(),
                encryptPassword,
                registerUserDTO.getRole()
        );

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


}