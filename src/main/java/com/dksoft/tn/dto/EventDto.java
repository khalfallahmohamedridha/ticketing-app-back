package com.dksoft.tn.dto;

import java.util.List;

public record EventDto(
        long id,
        String imageUrl,
        String title,
        String description,
        String type,
        Long categoryId,
        List<EventDateDto> dates
) {}