package com.example.security_filter_chain.security.provider;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.security_filter_chain.common.RequestContext;
import com.example.security_filter_chain.security.token.JwtBasedAuthenticationToken;

public class JwtBasedAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtBasedAuthenticationToken auth = (JwtBasedAuthenticationToken) authentication;
        System.out.println("############ from jwt authentication");

        // TODO: Perform actual authentication logic and prepare request context
        String token = auth.getJwt();
        RequestContext ctx = RequestContext.builder()
                .email("user@gmail.com")
                .entityId(UUID.randomUUID())
                .entityType(RequestContext.EntityType.CUSTOMER)
                .locale(Locale.getDefault())
                .sellerEmail("seller-user@gmail.com")
                .sellerLocationId(UUID.randomUUID())
                .build();

        if (!Objects.isNull(token)) {
            return new JwtBasedAuthenticationToken(token, ctx);
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("############ from jwt provider");

        return JwtBasedAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
