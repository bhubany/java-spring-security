package com.example.security_filter_chain.security.token;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.example.security_filter_chain.common.RequestContext;

import lombok.Getter;

public class JwtBasedAuthenticationToken implements Authentication {
    private boolean isAuthenticated;
    private RequestContext context;
    @Getter
    private final String jwt;

    // Constructor to be used pre-authentication
    public JwtBasedAuthenticationToken(String jwt) {
        this.jwt = jwt;
    }

    // Constructor to be used after successful authentication
    public JwtBasedAuthenticationToken(String jwt, RequestContext context) {
        this.jwt = jwt;
        this.context = context;
        this.isAuthenticated = true;
    }

    @Override
    public String getName() {
        return context.getEntityType().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getDetails() {
        return context;
    }

    @Override
    public Object getPrincipal() {
        return context;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Not supported to set authentication manually, use constructor instead");
    }

}
