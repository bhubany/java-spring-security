package com.example.security_filter_chain.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "com.example.security-filter-chain")
public class AppConfiguration {
    private CookieConfiguration cookie;

    @Getter
    @Setter
    public static class CookieConfiguration {
        private String name;
        private int expiresIn;
    }
}
