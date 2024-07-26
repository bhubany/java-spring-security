package com.example.security_filter_chain.security.token;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.example.security_filter_chain.common.RequestContext;

import lombok.Getter;

public class CookieBasedAuthenticationToken implements Authentication {
    private boolean isAuthenticated;
    private RequestContext ctx;

    @Getter
    private final String token;

    // to set token
    public CookieBasedAuthenticationToken(String token) {
        this.token = token;
    }

    // to authorize and set user details
    public CookieBasedAuthenticationToken(String token, RequestContext context) {
        this.token = token;
        this.ctx = context;
        this.isAuthenticated = true;
    }

    @Override
    public String getName() {
        return ctx.getEntityType().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return ctx;
    }

    @Override
    public Object getPrincipal() {
        return ctx;
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
