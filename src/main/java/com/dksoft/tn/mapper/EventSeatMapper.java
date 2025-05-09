package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventSeatDto;
import com.dksoft.tn.entity.EventLocation;
import com.dksoft.tn.entity.EventSeat;
import org.springframework.stereotype.Component;

@Component
public interface EventSeatMapper {
    EventSeat fromEventSeatDTO(EventSeatDto dto, EventLocation eventLocation);
    EventSeatDto fromEventSeat(EventSeat eventSeat);
}