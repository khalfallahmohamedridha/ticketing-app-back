// EventSeatDto.java
package com.dksoft.tn.dto;

public record EventSeatDto(
        long id,
        String section,
        String row,
        String seatNumber,
        long price,
        int quantity
) {}