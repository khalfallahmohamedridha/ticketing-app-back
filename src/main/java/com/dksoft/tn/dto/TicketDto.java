package com.dksoft.tn.dto;

public record TicketDto(
        long id,
        String numTicket,
        String place,
        long eventId
) {
}
