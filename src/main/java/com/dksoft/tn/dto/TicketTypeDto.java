package com.dksoft.tn.dto;

public record TicketTypeDto(
        long id,
        String name,
        String description,
        long price,
        String currency,
        boolean isActive
) {
}