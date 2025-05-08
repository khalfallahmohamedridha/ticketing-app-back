package com.dksoft.tn.dto;


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
        Long categoryId
) {}
