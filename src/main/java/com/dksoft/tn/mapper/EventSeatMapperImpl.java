package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventSeatDto;
import com.dksoft.tn.entity.EventLocation;
import com.dksoft.tn.entity.EventSeat;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EventSeatMapperImpl implements EventSeatMapper {

    @Override
    public EventSeat fromEventSeatDTO(EventSeatDto dto, EventLocation eventLocation) {
        if (dto == null) {
            return null;
        }

        EventSeat.EventSeatBuilder builder = EventSeat.builder();

        builder.id(dto.id());
        builder.section(dto.section());
        builder.row(dto.row());
        builder.seatNumber(dto.seatNumber());
        builder.price(dto.price());
        builder.quantity(dto.quantity());
        builder.eventLocation(eventLocation);

        return builder.build();
    }

    @Override
    public EventSeatDto fromEventSeat(EventSeat eventSeat) {
        if (eventSeat == null) {
            return null;
        }

        return new EventSeatDto(
                eventSeat.getId(),
                eventSeat.getSection(),
                eventSeat.getRow(),
                eventSeat.getSeatNumber(),
                eventSeat.getPrice(),
                eventSeat.getQuantity()
        );
    }
}