package com.example.security_filter_chain.common;

import java.util.Locale;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestContext {
    private EntityType entityType;
    private UUID entityId;
    private String email;
    private String sellerEmail;
    private UUID sellerLocationId;
    private Locale locale;

    public enum EntityType {
        CUSTOMER, SELLER
    }
}