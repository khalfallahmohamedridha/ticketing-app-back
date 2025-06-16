package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.Event;
import lombok.NonNull;

public interface EventMapper {
    Event fromEventDTO(@NonNull EventDto dto);

    // 🔁 Mapper Entity vers DTO
    EventDto fromEvent(Event event);
}
