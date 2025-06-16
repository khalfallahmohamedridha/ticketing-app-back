package com.dksoft.tn.dto;

import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Place;
import com.dksoft.tn.entity.Ticket;
import com.dksoft.tn.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public record EventDto(
        long id,
        List<String> imageUrls,
        String title,
        String description,
        String shortDescription,
<<<<<<< Updated upstream
        Long placeId,
        List<Long> categoryIds,
        Long organizerId,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<String> tags,
        List<EventDayDto> eventDays
=======
        Long categoryId,
        Long organizerId,
        LocalDate dateDebut,
        LocalDate dateFin,
        String tags,
        List<Ticket> tickets,
        List<Place> places

>>>>>>> Stashed changes
) {

}