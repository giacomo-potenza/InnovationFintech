package com.giacomo.potenza.InnovationFintech.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.giacomo.potenza.InnovationFintech.dto.LoginRequest;
import com.giacomo.potenza.InnovationFintech.dto.LoginResponse;
import com.giacomo.potenza.InnovationFintech.service.AuthService;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);

            return ResponseEntity.ok(response);
            //return ResponseEntity.ok(Collections.singletonMap("status", "ok"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}