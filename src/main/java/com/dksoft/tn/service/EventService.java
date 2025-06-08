package com.dksoft.tn.service;

import com.dksoft.tn.entity.Event;
import com.dksoft.tn.exception.EventNotFound;
import com.dksoft.tn.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private static final String UPLOAD_DIR = "uploads/images/";

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
    }

    public Optional<Event> getEventById(long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

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
    }

    public void deleteEvent(long id) throws EventNotFound {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFound("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }

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
}