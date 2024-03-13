package com.rijo.springjwtnew.jwtnew.services;

import com.rijo.springjwtnew.jwtnew.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);

}
