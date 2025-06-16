package com.dksoft.tn.service;

import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.*;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.exception.EventNotFound;
import com.dksoft.tn.mapper.EventMapper;
import com.dksoft.tn.repository.CategoryRepository;
import com.dksoft.tn.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
<<<<<<< Updated upstream
import org.springframework.web.multipart.MultipartFile;
=======
import org.springframework.transaction.annotation.Transactional;
>>>>>>> Stashed changes

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class EventService {

    private final EventRepository eventRepository;
<<<<<<< Updated upstream
    private static final String UPLOAD_DIR = "uploads/images/";
=======
    private final EventMapper mapper;
    private final CategoryRepository categoryRepository;
>>>>>>> Stashed changes

    public EventService(EventRepository eventRepository, EventMapper mapper, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
<<<<<<< Updated upstream
        // Ensure upload directory exists
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }
    }

    public Event createEvent(Event event, MultipartFile[] images) throws IOException {
        validateEvent(event);
        handleImageUploads(event, images);
        return eventRepository.save(event);
=======
        this.mapper = mapper;
    }

    public EventDto save(EventDto eventDto) {
        Category category = categoryRepository.findById(eventDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id = '" + eventDto.categoryId() + "' not found."));
        Event event = mapper.fromEventDTO(eventDto);
        event.setCategory(category);
        Event eventSaved = eventRepository.save(event);
        return mapper.fromEvent(eventSaved);
>>>>>>> Stashed changes
    }


    public void deleteById(Long id) {
        eventRepository.deleteById(id);
        log.info("Event deleted successfully with ID: {}", id);
    }

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

<<<<<<< Updated upstream
    public Event updateEvent(long id, Event event, MultipartFile[] images) throws EventNotFound, IOException {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFound("Event not found with id: " + id));
        validateEvent(event);

        existingEvent.setImageUrls(event.getImageUrls() != null ? event.getImageUrls() : new ArrayList<>());
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setShortDescription(event.getShortDescription());
        existingEvent.setPlace(event.getPlace());
        existingEvent.setCategories(event.getCategories());
        existingEvent.setOrganizer(event.getOrganizer());
        existingEvent.setIsActive(event.isActive());
        existingEvent.setTags(event.getTags());
        existingEvent.setEventDays(event.getEventDays());

        // Ensure eventDays reference this event
        if (event.getEventDays() != null) {
            event.getEventDays().forEach(eventDay -> eventDay.setEvent(existingEvent));
        }

        handleImageUploads(existingEvent, images);
        return eventRepository.save(existingEvent);
=======
    public EventDto getEventById(Long id) throws EventNotFound {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFound("Event with id = '" + id + "' not found."));
        return mapper.fromEvent(event);
>>>>>>> Stashed changes
    }

    public List<EventDto> getAllEventDtos() {
        return eventRepository.findAll()
                .stream()
                .map(mapper::fromEvent)
                .toList();
    }

<<<<<<< Updated upstream
    private void handleImageUploads(Event event, MultipartFile[] images) throws IOException {
        if (images != null && images.length > 0) {
            List<String> imageUrls = event.getImageUrls() != null ? event.getImageUrls() : new ArrayList<>();
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String fileExtension = getFileExtension(image.getOriginalFilename());
                    String fileName = UUID.randomUUID().toString() + fileExtension;
                    Path filePath = Paths.get(UPLOAD_DIR, fileName);
                    Files.write(filePath, image.getBytes());
                    imageUrls.add("/Uploads/images/" + fileName);
                }
            }
            event.setImageUrls(imageUrls);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ".jpg"; // Default extension
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private void validateEvent(Event event) {
        if (event.getTitle() == null || event.getTitle().isBlank()) {
            throw new IllegalArgumentException("Event title cannot be empty");
        }
        if (event.getDescription() == null || event.getDescription().isBlank()) {
            throw new IllegalArgumentException("Event description cannot be empty");
        }
        if (event.getShortDescription() == null || event.getShortDescription().isBlank()) {
            throw new IllegalArgumentException("Event short description cannot be empty");
        }
        if (event.getPlace() == null) {
            throw new IllegalArgumentException("Event place cannot be null");
        }
        if (event.getOrganizer() == null) {
            throw new IllegalArgumentException("Event organizer cannot be null");
        }
        if (event.getEventDays() == null || event.getEventDays().isEmpty()) {
            throw new IllegalArgumentException("Event must have at least one event day");
        }
        boolean hasTicketType = event.getEventDays().stream()
                .anyMatch(eventDay -> eventDay.getEventDayTicketTypes() != null &&
                        !eventDay.getEventDayTicketTypes().isEmpty());
        if (!hasTicketType) {
            throw new IllegalArgumentException("At least one event day must have a ticket type");
        }
    }
=======


>>>>>>> Stashed changes
}