package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDateDto;
import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.EventDate;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMapperImpl implements EventMapper {
    @Autowired
    private EventDateMapper eventDateMapper;

    @Override
    public Event fromEventDTO(@NonNull EventDto dto) {
        Category category = new Category();
        category.setId(dto.categoryId());

        Event event = Event.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .type(dto.type())
                .imageUrl(dto.imageUrl())
                .category(category)
                .build();

        if (dto.dates() != null) {
            List<EventDate> dates = dto.dates().stream()
                    .map(dateDto -> eventDateMapper.fromEventDateDTO(dateDto, event))
                    .collect(Collectors.toList());
            event.setDates(dates);
        }

        return event;
    }

    @Override
    public EventDto fromEvent(Event event) {
        List<EventDateDto> dateDtos = event.getDates() != null
                ? event.getDates().stream()
                .map(eventDateMapper::fromEventDate)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return new EventDto(
                event.getId(),
                event.getImageUrl(),
                event.getTitle(),
                event.getDescription(),
                event.getType(),
                event.getCategory() != null ? event.getCategory().getId() : null,
                dateDtos
        );
    }
}
