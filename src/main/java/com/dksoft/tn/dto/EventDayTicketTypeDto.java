package com.dksoft.tn.dto;

public record EventDayTicketTypeDto(
        TicketTypeDto ticketType,
        int maxNumber
) {
}