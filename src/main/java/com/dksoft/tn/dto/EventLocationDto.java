// EventLocationDto.java
package com.dksoft.tn.dto;

import java.util.List;

public record EventLocationDto(
        long id,
        String name,
        String address,
        List<EventSeatDto> seats
) {}