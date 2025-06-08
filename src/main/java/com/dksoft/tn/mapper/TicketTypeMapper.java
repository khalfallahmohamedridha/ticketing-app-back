package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.TicketTypeDto;
import com.dksoft.tn.entity.TicketType;
import lombok.NonNull;

public interface TicketTypeMapper {
    TicketType fromTicketTypeDto(@NonNull TicketTypeDto dto);
    TicketTypeDto fromTicketType(@NonNull TicketType ticketType);
}