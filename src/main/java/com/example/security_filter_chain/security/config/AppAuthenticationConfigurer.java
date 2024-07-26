package com.example.security_filter_chain.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security_filter_chain.security.filter.CookieBasedAuthenticationFilter;
import com.example.security_filter_chain.security.filter.JwtBasedAuthenticationFilter;
import com.example.security_filter_chain.security.provider.CookieBasedAuthenticationProvider;
import com.example.security_filter_chain.security.provider.JwtBasedAuthenticationProvider;

@Configuration
public class AppAuthenticationConfigurer extends AbstractHttpConfigurer<AppAuthenticationConfigurer, HttpSecurity> {

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(new JwtBasedAuthenticationProvider(), new CookieBasedAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtBasedAuthenticationFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new CookieBasedAuthenticationFilter(authenticationManager()),
                JwtBasedAuthenticationFilter.class);

    }

}
