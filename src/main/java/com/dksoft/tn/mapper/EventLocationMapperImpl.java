package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventLocationDto;
import com.dksoft.tn.dto.EventSeatDto;
import com.dksoft.tn.entity.EventDate;
import com.dksoft.tn.entity.EventLocation;
import com.dksoft.tn.entity.EventSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class EventLocationMapperImpl implements EventLocationMapper {

    private final EventSeatMapper eventSeatMapper;

    @Autowired
    public EventLocationMapperImpl(EventSeatMapper eventSeatMapper) {
        this.eventSeatMapper = eventSeatMapper;
    }

    @Override
    public EventLocation fromEventLocationDTO(EventLocationDto dto, EventDate eventDate) {
        if (dto == null) {
            return null;
        }

        EventLocation.EventLocationBuilder builder = EventLocation.builder();

        builder.id(dto.id());
        builder.name(dto.name());
        builder.address(dto.address());
        builder.eventDate(eventDate);

        EventLocation eventLocation = builder.build();

        if (dto.seats() != null && !dto.seats().isEmpty()) {
            List<EventSeat> seats = new ArrayList<>();
            for (EventSeatDto seatDto : dto.seats()) {
                EventSeat seat = eventSeatMapper.fromEventSeatDTO(seatDto, eventLocation);
                if (seat != null) {
                    seats.add(seat);
                }
            }
            eventLocation.setSeats(seats);
        }

        return eventLocation;
    }

    @Override
    public EventLocationDto fromEventLocation(EventLocation eventLocation) {
        if (eventLocation == null) {
            return null;
        }

        List<EventSeatDto> seatDtos = new ArrayList<>();

        if (eventLocation.getSeats() != null) {
            for (EventSeat seat : eventLocation.getSeats()) {
                EventSeatDto seatDto = eventSeatMapper.fromEventSeat(seat);
                if (seatDto != null) {
                    seatDtos.add(seatDto);
                }
            }
        }

        return new EventLocationDto(
                eventLocation.getId(),
                eventLocation.getName(),
                eventLocation.getAddress(),
                seatDtos
        );
    }
}