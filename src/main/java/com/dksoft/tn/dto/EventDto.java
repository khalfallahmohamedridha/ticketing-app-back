package com.dksoft.tn.dto;

import java.util.List;

public record EventDto(
        long id,
        String imageUrl,
        String title,
        String description,
        String date,
        String hour,
        String location,
        long price,
        String type,
        Long categoryId,
        List<TicketDto> tickets // ✅ Utilisation du TicketDto ici au lieu de Ticket
) {}
