package com.example.security_filter_chain.security.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security_filter_chain.security.token.CookieBasedAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CookieBasedAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public CookieBasedAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // this.appConfig = new AppConfiguration();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("############### From cookie based authentication filter");
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals("securityFilterChain", cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new CookieBasedAuthenticationToken(token));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("Cookie based authentication filter: ", e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

}
