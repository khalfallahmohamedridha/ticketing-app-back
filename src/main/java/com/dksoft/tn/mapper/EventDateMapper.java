package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDateDto;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.EventDate;
import lombok.NonNull;

public interface EventDateMapper {
    EventDate fromEventDateDTO(@NonNull EventDateDto dto, Event event);
    EventDateDto fromEventDate(EventDate eventDate);
}