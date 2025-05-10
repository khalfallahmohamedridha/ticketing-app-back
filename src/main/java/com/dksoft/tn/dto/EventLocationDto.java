package com.dksoft.tn.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EventLocationDto(
        long id,

        @NotBlank(message = "Location name is required")
        @Size(min = 3, max = 100, message = "Location name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "Address is required")
        @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
        String address,

        @Valid
        List<EventSeatDto> seats
) {}