package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDayDto;
import com.dksoft.tn.entity.EventDay;
import lombok.NonNull;

public interface EventDayMapper {
    EventDay fromEventDayDto(@NonNull EventDayDto dto);
    EventDayDto fromEventDay(@NonNull EventDay eventDay);
}