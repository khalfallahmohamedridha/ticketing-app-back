package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventLocationDto;
import com.dksoft.tn.entity.EventDate;
import com.dksoft.tn.entity.EventLocation;
import org.springframework.stereotype.Component;

@Component
public interface EventLocationMapper {
    EventLocation fromEventLocationDTO(EventLocationDto dto, EventDate eventDate);
    EventLocationDto fromEventLocation(EventLocation eventLocation);
}