package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.TicketDto;
import com.dksoft.tn.entity.Ticket;
import lombok.NonNull;

public interface TicketMapper {
    Ticket fromTicketDto(@NonNull TicketDto dto);
    TicketDto fromTicket(Ticket ticket);
}
