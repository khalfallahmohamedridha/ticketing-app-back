package com.dksoft.tn.dto;

import com.dksoft.tn.validation.FutureDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record EventDateDto(
        long id,

        @NotBlank(message = "Date is required")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in format YYYY-MM-DD")
        @FutureDate(message = "Event date must be today or in the future")
        String date,

        @NotBlank(message = "Hour is required")
        @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Hour must be in format HH:MM")
        String hour,

        @Valid
        List<EventLocationDto> locations
) {}