package com.example.security_filter_chain.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security_filter_chain.security.token.JwtBasedAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtBasedAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public JwtBasedAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("############### From jwt based authentication filter");

        String token = request.getHeader("Authorization");

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Authentication authentication = this.authenticationManager
                    .authenticate(new JwtBasedAuthenticationToken(token));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.error("Error on JWT based authentication filter: ", e);
        } finally {

            filterChain.doFilter(request, response);
        }

    }

}
