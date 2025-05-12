package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventMapperImpl implements EventMapper {


    @Override
    public Event fromEventDTO(@NonNull EventDto dto) {
        Category category = new Category();
        category.setId(dto.categoryId());

        return Event.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .date(dto.date())
                .hour(dto.hour())
                .place(dto.place())
                .price(dto.price())
                .type(dto.type())
                .imageUrl(dto.imageUrl())      // 🔹 Nouveau champ : image
                .category(category)
                .build();
    }

    // 🔁 Mapper Entity vers DTO
    @Override
    public EventDto fromEvent(Event event) {
        return new EventDto(
                event.getId(),
                event.getImageUrl(),            // 🔹 Retourner imageUrl dans le DTO
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getHour(),
                event.getPlace(),
                event.getPrice(),
                event.getType(),
                event.getCategory() != null ? event.getCategory().getId() : null,
                event.getTickets()
        );
    }
}
