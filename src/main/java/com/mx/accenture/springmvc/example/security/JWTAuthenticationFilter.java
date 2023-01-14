package com.mx.accenture.springmvc.example.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // Read credentials from request
        AuthCredentials authCredentials = new AuthCredentials();

        try{
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        }catch (IOException e){

        }

        // Attempt authentication
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getUsername(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // Create token
        User userDetails = (User) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getUsername());

        // Add token to response header
        response.addHeader("Authorization", "Bearer "+ token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
