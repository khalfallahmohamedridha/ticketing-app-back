package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDayDto;
import com.dksoft.tn.dto.EventDayTicketTypeDto;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.EventDay;
import com.dksoft.tn.entity.EventDayTicketType;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventDayMapperImpl implements EventDayMapper {

    private final TicketTypeMapper ticketTypeMapper;

    public EventDayMapperImpl(TicketTypeMapper ticketTypeMapper) {
        this.ticketTypeMapper = ticketTypeMapper;
    }

    @Override
    public EventDay fromEventDayDto(@NonNull EventDayDto dto) {
        Event event = new Event();
        event.setId(dto.eventId());

        List<EventDayTicketType> eventDayTicketTypes = dto.eventDayTicketTypes() != null ? dto.eventDayTicketTypes().stream()
                .map(dtoTicket -> {
                    EventDayTicketType eventDayTicketType = new EventDayTicketType();
                    eventDayTicketType.setTicketType(ticketTypeMapper.fromTicketTypeDto(dtoTicket.ticketType()));
                    eventDayTicketType.setMaxNumber(dtoTicket.maxNumber());
                    return eventDayTicketType;
                })
                .collect(Collectors.toList()) : null;

        return EventDay.builder()
                .id(dto.id())
                .dateTime(dto.dateTime())
                .isActive(dto.isActive())
                .maxNumber(dto.maxNumber())
                .event(event)
                .eventDayTicketTypes(eventDayTicketTypes)
                .build();
    }

    @Override
    public EventDayDto fromEventDay(@NonNull EventDay eventDay) {
        List<EventDayTicketTypeDto> eventDayTicketTypeDtos = eventDay.getEventDayTicketTypes() != null ? eventDay.getEventDayTicketTypes().stream()
                .map(eventDayTicketType -> new EventDayTicketTypeDto(
                        ticketTypeMapper.fromTicketType(eventDayTicketType.getTicketType()),
                        eventDayTicketType.getMaxNumber()
                ))
                .collect(Collectors.toList()) : null;

        return new EventDayDto(
                eventDay.getId(),
                eventDay.getDateTime(),
                eventDay.isActive(),
                eventDay.getMaxNumber(),
                eventDay.getEvent() != null ? eventDay.getEvent().getId() : null,
                eventDayTicketTypeDtos
        );
    }
}