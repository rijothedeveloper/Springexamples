package com.rijo.springjwtnew.jwtnew.services.impl;

import com.rijo.springjwtnew.jwtnew.dto.JwtAuthenticationResponse;
import com.rijo.springjwtnew.jwtnew.dto.RefreshTokenRequest;
import com.rijo.springjwtnew.jwtnew.dto.SigninRequest;
import com.rijo.springjwtnew.jwtnew.dto.SignupRequest;
import com.rijo.springjwtnew.jwtnew.entities.Role;
import com.rijo.springjwtnew.jwtnew.entities.User;
import com.rijo.springjwtnew.jwtnew.repository.UserRepository;
import com.rijo.springjwtnew.jwtnew.services.AuthenticationService;
import com.rijo.springjwtnew.jwtnew.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public User signup(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setFirstname(signupRequest.getFirstName());
        user.setLastname(signupRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        var user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("invalid email"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        if(!jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            return null;
        }
        var jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
        return jwtAuthenticationResponse;
    }
}
