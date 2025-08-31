
package com.giacomo.potenza.InnovationFintech.service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.giacomo.potenza.InnovationFintech.dto.LoginResponse;
import com.giacomo.potenza.InnovationFintech.dto.LoginRequest;
import com.giacomo.potenza.InnovationFintech.dto.UserDto;
import com.giacomo.potenza.InnovationFintech.entity.User;
import com.giacomo.potenza.InnovationFintech.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private  AuthenticationManager authenticationManager;
    private  JwtUtil JwtUtil;
    private  UserService userService;


    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.matricola, request.password)
        );

        User user = userService.getUserByMatricola(request.matricola);
        String token = JwtUtil.generateToken(user);

        return new LoginResponse();
    }
}
