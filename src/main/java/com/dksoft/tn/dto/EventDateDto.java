// EventDateDto.java
package com.dksoft.tn.dto;

import java.util.List;

public record EventDateDto(
        long id,
        String date,
        String hour,
        List<EventLocationDto> locations
) {}