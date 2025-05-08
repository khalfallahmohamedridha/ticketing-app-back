package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.CategoryDto;
import com.dksoft.tn.dto.TicketDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.Ticket;
import lombok.NonNull;
import org.springframework.stereotype.Service;


@Service

public class TicketMapperImpl implements TicketMapper {
    @Override
    public Ticket fromTicketDto(@NonNull TicketDto dto) {
        Event event = new Event();
        event.setId(dto.eventId());

        return Ticket.builder()
                .id(dto.id())
                .numTicket(dto.numTicket())
                .place(dto.place())
                .event(event)
                .build();
    }

    @Override
    public TicketDto fromTicket(Ticket ticket) {
        return new TicketDto(ticket.getId(), ticket.getNumTicket(), ticket.getPlace(),
                ticket.getEvent() != null ? ticket.getEvent().getId() : null);
    }
}
