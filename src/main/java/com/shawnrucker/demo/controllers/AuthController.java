package com.shawnrucker.demo.controllers;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shawnrucker.demo.configs.JwtTokenProvider;
import com.shawnrucker.demo.models.User;
import com.shawnrucker.demo.repositories.UserRepository;
import com.shawnrucker.demo.services.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private CustomUserDetailsService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try {
            String userName = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, data.getPassword()));
            String token = jwtTokenProvider.createToken(userName, this.users.findByUsername(userName).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", userName);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid User Name or Password supplied");
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        userService.registerUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }

}
