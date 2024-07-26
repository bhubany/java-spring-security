package com.example.security_filter_chain.security.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.example.security_filter_chain.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .with(new AppAuthenticationConfigurer(), Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/web/auth").permitAll();
                    authorize.requestMatchers("/web/public").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling((e) -> {
                    e.authenticationEntryPoint((req, resp, authException) -> {
                        preparedUnauthorizedResponse(resp, ApiResponse.ErrorType.UNAUTHORIZED,
                                authException.getMessage());
                    });
                });

        return http.build();
    }

    private void preparedUnauthorizedResponse(HttpServletResponse response, ApiResponse.ErrorType errorType,
            String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .error(ApiResponse.APIError.builder()
                        .type(errorType.getType())
                        .code(errorType.getCode())
                        .error_user_title("Unauthorized access")
                        .error_user_msg("Unauthorized access: either token is invalid or expired")
                        .message(message)
                        .build())
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
