package com.dksoft.tn.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventSeatDto(
        long id,

        @NotBlank(message = "Section is required")
        String section,

        String row,

        String seatNumber,

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be positive")
        long price,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {}