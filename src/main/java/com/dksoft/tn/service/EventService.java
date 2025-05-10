package com.dksoft.tn.service;

import com.dksoft.tn.dto.*;
import com.dksoft.tn.entity.*;
import com.dksoft.tn.exception.*;
import com.dksoft.tn.mapper.*;
import com.dksoft.tn.repository.*;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import com.dksoft.tn.dto.EventDateDto;
import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.dto.EventLocationDto;
import com.dksoft.tn.dto.EventSeatDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.EventDate;
import com.dksoft.tn.entity.EventLocation;
import com.dksoft.tn.entity.EventSeat;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.exception.EventDateNotFoundException;
import com.dksoft.tn.exception.EventLocationNotFoundException;
import com.dksoft.tn.exception.EventNotFound;
import com.dksoft.tn.mapper.EventDateMapper;
import com.dksoft.tn.mapper.EventLocationMapper;
import com.dksoft.tn.mapper.EventMapper;
import com.dksoft.tn.mapper.EventSeatMapper;
import com.dksoft.tn.repository.CategoryRepository;
import com.dksoft.tn.repository.EventDateRepository;
import com.dksoft.tn.repository.EventLocationRepository;
import com.dksoft.tn.repository.EventRepository;
import com.dksoft.tn.repository.EventSeatRepository;

@Service
@Slf4j
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EventDateRepository eventDateRepository;
    private final EventLocationRepository eventLocationRepository;
    private final EventSeatRepository eventSeatRepository;
    private final EventMapper mapper;
    private final EventDateMapper dateMapper;
    private final EventLocationMapper locationMapper;
    private final EventSeatMapper seatMapper;
    private final CategoryRepository categoryRepository;

    public EventService(
            EventRepository eventRepository,
            EventDateRepository eventDateRepository,
            EventLocationRepository eventLocationRepository,
            EventSeatRepository eventSeatRepository,
            EventMapper mapper,
            EventDateMapper dateMapper,
            EventLocationMapper locationMapper,
            EventSeatMapper seatMapper,
            CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.eventDateRepository = eventDateRepository;
        this.eventLocationRepository = eventLocationRepository;
        this.eventSeatRepository = eventSeatRepository;
        this.mapper = mapper;
        this.dateMapper = dateMapper;
        this.locationMapper = locationMapper;
        this.seatMapper = seatMapper;
        this.categoryRepository = categoryRepository;
    }

    public EventDto save(EventDto eventDto) {
        Category category = categoryRepository.findById(eventDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id = '" + eventDto.categoryId() + "' not found."));
        validateEventDates(eventDto);

        Event event = mapper.fromEventDTO(eventDto);
        event.setCategory(category);
        Event eventSaved = eventRepository.save(event);
        return mapper.fromEvent(eventSaved);
    }

    public EventDto update(String id, EventDto eventDto) throws EventNotFound {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFound("Event with id = '" + id + "' not found."));

        // Update basic event fields
        event.setTitle(eventDto.title());
        event.setDescription(eventDto.description());
        event.setType(eventDto.type());

        // Update category if provided
        if (eventDto.categoryId() != null) {
            Category category = categoryRepository.findById(eventDto.categoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category with id = '" + eventDto.categoryId() + "' not found."));
            event.setCategory(category);
        }

        // Handle dates if provided
        if (eventDto.dates() != null) {
            // Clear existing dates and add new ones
            event.getDates().clear();

            for (EventDateDto dateDto : eventDto.dates()) {
                EventDate date = dateMapper.fromEventDateDTO(dateDto, event);
                event.addDate(date);
            }
        }

        Event eventUpdate = eventRepository.save(event);
        log.info("Event updated successfully with ID: {}", id);
        return mapper.fromEvent(eventUpdate);
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

    // Get event entity by ID
    public Event getEventByIdEntity(long id) throws EventNotFound {
        return eventRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new EventNotFound("Event not found with ID: " + id));
    }

    // Save event entity
    public Event saveEntity(Event event) {
        return eventRepository.save(event);
    }

    // New methods for managing dates, locations, and seats

    public EventDateDto addDateToEvent(Long eventId, EventDateDto dateDto) throws EventNotFound {
        Event event = getEventByIdEntity(eventId);
        EventDate date = dateMapper.fromEventDateDTO(dateDto, event);
        date = eventDateRepository.save(date);
        event.addDate(date);
        eventRepository.save(event);
        return dateMapper.fromEventDate(date);
    }

    public List<EventDateDto> getEventDates(Long eventId) throws EventNotFound {
        // Verify event exists
        getEventByIdEntity(eventId);

        return eventDateRepository.findByEventId(eventId).stream()
                .map(dateMapper::fromEventDate)
                .collect(Collectors.toList());
    }

    public EventLocationDto addLocationToDate(Long dateId, EventLocationDto locationDto) {
        EventDate date = eventDateRepository.findById(dateId)
                .orElseThrow(() -> new EventDateNotFoundException("Event date not found with ID: " + dateId));

        EventLocation location = locationMapper.fromEventLocationDTO(locationDto, date);
        location = eventLocationRepository.save(location);

        date.getLocations().add(location);
        eventDateRepository.save(date);

        return locationMapper.fromEventLocation(location);
    }

    public List<EventLocationDto> getDateLocations(Long dateId) {
        // Verify date exists
        eventDateRepository.findById(dateId)
                .orElseThrow(() -> new EventDateNotFoundException("Event date not found with ID: " + dateId));

        return eventLocationRepository.findByEventDateId(dateId).stream()
                .map(locationMapper::fromEventLocation)
                .collect(Collectors.toList());
    }

    public EventSeatDto addSeatToLocation(Long locationId, EventSeatDto seatDto) {
        EventLocation location = eventLocationRepository.findById(locationId)
                .orElseThrow(() -> new EventLocationNotFoundException("Event location not found with ID: " + locationId));

        EventSeat seat = seatMapper.fromEventSeatDTO(seatDto, location);
        seat = eventSeatRepository.save(seat);

        location.getSeats().add(seat);
        eventLocationRepository.save(location);

        return seatMapper.fromEventSeat(seat);
    }

    public List<EventSeatDto> getLocationSeats(Long locationId) {
        // Verify location exists
        eventLocationRepository.findById(locationId)
                .orElseThrow(() -> new EventLocationNotFoundException("Event location not found with ID: " + locationId));

        return eventSeatRepository.findByEventLocationId(locationId).stream()
                .map(seatMapper::fromEventSeat)
                .collect(Collectors.toList());
    }

    //validation methods
    private void validateEventDates(EventDto eventDto) {
        if (eventDto.dates() == null || eventDto.dates().isEmpty()) {
            return; // No dates to validate
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        for (EventDateDto dateDto : eventDto.dates()) {
            // Skip if date is null (will be caught by @NotBlank)
            if (dateDto.date() == null || dateDto.date().isEmpty()) {
                continue;
            }

            try {
                LocalDate eventDate = LocalDate.parse(dateDto.date(), formatter);
                if (eventDate.isBefore(today)) {
                    throw new ValidationException("Event date cannot be in the past: " + dateDto.date());
                }
            } catch (Exception e) {
                throw new ValidationException("Invalid date format: " + dateDto.date());
            }

            // Validate locations
            validateEventLocations(dateDto);
        }
    }

    private void validateEventLocations(EventDateDto dateDto) {
        if (dateDto.locations() == null || dateDto.locations().isEmpty()) {
            return; // No locations to validate
        }

        for (EventLocationDto locationDto : dateDto.locations()) {
            // Validate seats
            validateEventSeats(locationDto);
        }
    }

    private void validateEventSeats(EventLocationDto locationDto) {
        if (locationDto.seats() == null || locationDto.seats().isEmpty()) {
            return; // No seats to validate
        }

        for (EventSeatDto seatDto : locationDto.seats()) {
            if (seatDto.price() < 0) {
                throw new ValidationException("Seat price cannot be negative: " + seatDto.price());
            }

            if (seatDto.quantity() < 1) {
                throw new ValidationException("Seat quantity must be at least 1: " + seatDto.quantity());
            }
        }
    }
}