package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.TicketTypeDto;
import com.dksoft.tn.entity.TicketType;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TicketTypeMapperImpl implements TicketTypeMapper {

    @Override
    public TicketType fromTicketTypeDto(@NonNull TicketTypeDto dto) {
        return TicketType.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .currency(dto.currency())
                .isActive(dto.isActive())
                .build();
    }

    @Override
    public TicketTypeDto fromTicketType(@NonNull TicketType ticketType) {
        return new TicketTypeDto(
                ticketType.getId(),
                ticketType.getName(),
                ticketType.getDescription(),
                ticketType.getPrice(),
                ticketType.getCurrency(),
                ticketType.isActive()
        );
    }
}