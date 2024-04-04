package com.dev.productionTracker.dto;

import com.dev.productionTracker.model.Role;
import lombok.Builder;

@Builder
public record CreateUserRequest(
        String firstName,
        String lastName,
        String username,
        String password,
        Role authorities
)
{
}
