// EventDateMapperImpl.java
package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDateDto;
import com.dksoft.tn.dto.EventLocationDto;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.EventDate;
import com.dksoft.tn.entity.EventLocation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventDateMapperImpl implements EventDateMapper {

    @Autowired
    private EventLocationMapper eventLocationMapper;

    @Override
    public EventDate fromEventDateDTO(@NonNull EventDateDto dto, Event event) {
        EventDate eventDate = EventDate.builder()
                .id(dto.id())
                .date(dto.date())
                .hour(dto.hour())
                .event(event)
                .build();

        if (dto.locations() != null) {
            List<EventLocation> locations = dto.locations().stream()
                    .map(locationDto -> eventLocationMapper.fromEventLocationDTO(locationDto, eventDate))
                    .collect(Collectors.toList());
            eventDate.setLocations(locations);
        }

        return eventDate;
    }

    @Override
    public EventDateDto fromEventDate(EventDate eventDate) {
        List<EventLocationDto> locationDtos = eventDate.getLocations() != null
                ? eventDate.getLocations().stream()
                .map(eventLocationMapper::fromEventLocation)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return new EventDateDto(
                eventDate.getId(),
                eventDate.getDate(),
                eventDate.getHour(),
                locationDtos
        );
    }
}