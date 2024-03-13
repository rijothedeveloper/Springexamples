package com.rijo.springjwtnew.jwtnew.services;

import com.rijo.springjwtnew.jwtnew.dto.JwtAuthenticationResponse;
import com.rijo.springjwtnew.jwtnew.dto.RefreshTokenRequest;
import com.rijo.springjwtnew.jwtnew.dto.SigninRequest;
import com.rijo.springjwtnew.jwtnew.dto.SignupRequest;
import com.rijo.springjwtnew.jwtnew.entities.User;

public interface AuthenticationService {
    User signup(SignupRequest signupRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
