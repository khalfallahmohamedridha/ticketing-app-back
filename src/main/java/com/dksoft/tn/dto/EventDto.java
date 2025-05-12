package com.dksoft.tn.dto;


import com.dksoft.tn.entity.Ticket;

import java.util.List;

public record EventDto(
        long id,
        String imageUrl,
        String title,
        String description,
        String date,
        String hour,
        String place,
        long price,
        String type,
        Long categoryId,
        List<Ticket> tickets
) {}
