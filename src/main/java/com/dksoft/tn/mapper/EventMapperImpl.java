package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.Ticket;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMapperImpl implements EventMapper {

    private final TicketMapper ticketMapper;

    public EventMapperImpl(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Override
    public Event fromEventDTO(@NonNull EventDto dto) {
        Category category = new Category();
        category.setId(dto.categoryId());

        List<Ticket> tickets = dto.tickets() != null ? dto.tickets().stream()
                .map(ticketMapper::fromTicketDto)
                .collect(Collectors.toList()) : null;

        return Event.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .date(dto.date())
                .hour(dto.hour())
                .location(dto.location())
                .price(dto.price())
                .type(dto.type())
                .imageUrl(dto.imageUrl())
                .category(category)
                .tickets(tickets)
                .build();
    }

    @Override
    public EventDto fromEvent(Event event) {
        List<com.dksoft.tn.dto.TicketDto> ticketDtos = event.getTickets() != null ? event.getTickets().stream()
                .map(ticketMapper::fromTicket)
                .collect(Collectors.toList()) : null;

        return new EventDto(
                event.getId(),
                event.getImageUrl(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getHour(),
                event.getLocation(),
                event.getPrice(),
                event.getType(),
                event.getCategory() != null ? event.getCategory().getId() : null,
                ticketDtos
        );
    }
}
