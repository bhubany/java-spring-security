package com.example.security_filter_chain.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private UUID id;
    private String name;
    private String email;
}
