package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.*;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventMapperImpl implements EventMapper {


    @Override
    public Event fromEventDTO(@NonNull EventDto dto) {
<<<<<<< Updated upstream
        List<Category> categories = dto.categoryIds() != null ? dto.categoryIds().stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toList()) : null;

        Place place = new Place();
        place.setId(dto.placeId());

        User organizer = new User();
        organizer.setId(dto.organizerId());

        List<EventDay> eventDays = dto.eventDays() != null ? dto.eventDays().stream()
                .map(eventDayMapper::fromEventDayDto)
                .collect(Collectors.toList()) : null;
=======
        Category category = new Category();
        category.setId(dto.categoryId());
        User organiser = new User();
        organiser.setId(dto.organizerId());
>>>>>>> Stashed changes

        return Event.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .shortDescription(dto.shortDescription())
<<<<<<< Updated upstream
                .place(place)
                .categories(categories)
                .organizer(organizer)
                .isActive(dto.isActive())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())
                .tags(dto.tags())
                .eventDays(eventDays)
=======
                .dateDebut(dto.dateDebut())
                .dateFin(dto.dateFin())
                .tag(dto.tags())
                .dateFin(dto.dateFin())



//                .imageUrl(dto.imageUrl())

                .category(category)
                .organizer(organiser)
>>>>>>> Stashed changes
                .build();
    }

    // 🔁 Mapper Entity vers DTO
    @Override
    public EventDto fromEvent(Event event) {
        return new EventDto(
                event.getId(),
                null,
                event.getTitle(),
                event.getDescription(),
                event.getShortDescription(),
<<<<<<< Updated upstream
                event.getPlace() != null ? event.getPlace().getId() : null,
                categoryIds,
                event.getOrganizer() != null ? event.getOrganizer().getId() : null,
                event.isActive(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getTags(),
                eventDayDtos
=======
                event.getCategory() != null ? event.getCategory().getId() : null,
                event.getOrganizer() != null ? event.getOrganizer().getId() : null,
                event.getDateDebut(),
                event.getDateFin(),
                event.getTag(),
                event.getTicket(),
                event.getPlaces()
>>>>>>> Stashed changes
        );
    }
}