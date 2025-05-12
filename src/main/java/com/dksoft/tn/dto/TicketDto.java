package com.dksoft.tn.dto;

public record TicketDto(
        Long id,
        String numTicket,
        String place,
        Long eventId,
        Long cartId
) {
}