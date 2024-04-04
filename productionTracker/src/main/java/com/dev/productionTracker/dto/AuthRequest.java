package com.dev.productionTracker.dto;

public record AuthRequest(
        String username,
        String password)
{
}
