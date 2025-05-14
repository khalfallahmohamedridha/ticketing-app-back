package com.dksoft.tn.service;

import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.exception.EventNotFound;
import com.dksoft.tn.mapper.EventMapper;
import com.dksoft.tn.repository.CategoryRepository;
import com.dksoft.tn.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper mapper;
    private final CategoryRepository categoryRepository;

    public EventService(EventRepository eventRepository, EventMapper mapper, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    public EventDto save(EventDto eventDto) {
        Category category = categoryRepository.findById(eventDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id = '" + eventDto.categoryId() + "' not found."));
        Event event = mapper.fromEventDTO(eventDto);
        event.setCategory(category);
        Event eventSaved = eventRepository.save(event);
        return mapper.fromEvent(eventSaved);
    }

    public EventDto update(String id, EventDto eventDto) throws EventNotFound {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFound("Event with id = '" + id + "' not found."));
        updateEventFields(event, eventDto);
        Event eventUpdate = eventRepository.save(event);
        log.info("Event updated successfully with ID: {}", id);
        return mapper.fromEvent(eventUpdate);
    }

    private void updateEventFields(@NotNull Event event, @NotNull EventDto eventDto) {
        event.setTitle(eventDto.title());
        event.setDescription(eventDto.description());
        event.setDate(eventDto.date());
        event.setHour(eventDto.hour());
        event.setPlace(eventDto.place());
        event.setPrice(eventDto.price());
        event.setType(eventDto.type());
    }

    public void deleteById(String id) {
        eventRepository.deleteById(id);
        log.info("Event deleted successfully with ID: {}", id);
    }

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    public EventDto getEventById(String id) throws EventNotFound {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFound("Event with id = '" + id + "' not found."));
        return mapper.fromEvent(event);
    }

    public List<EventDto> getAllEventDtos() {
        return eventRepository.findAll()
                .stream()
                .map(mapper::fromEvent)
                .toList();
    }


    public Event getEventByIdEntity(long id) throws EventNotFound {
        return eventRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new EventNotFound("Event not found with ID: " + id));
    }


    public void saveEntity(Event event) {
        eventRepository.save(event);
    }

}
