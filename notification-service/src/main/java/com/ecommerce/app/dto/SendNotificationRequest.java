package com.ecommerce.app.dto;

import jakarta.validation.constraints.NotNull;

public record SendNotificationRequest(
    @NotNull Long userId,
    @NotNull String type,
    @NotNull String recipient,
    @NotNull String payload
) {}