// Updated EventController.java
package com.dksoft.tn.controller;

import com.dksoft.tn.dto.*;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.exception.EventNotFound;
import com.dksoft.tn.mapper.EventMapper;
import com.dksoft.tn.repository.CategoryRepository;
import com.dksoft.tn.service.EventService;
import com.dksoft.tn.service.ImageService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final ImageService imageService;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;

    public EventController(EventService eventService, ImageService imageService, EventMapper eventMapper, CategoryRepository categoryRepository) {
        this.eventService = eventService;
        this.imageService = imageService;
        this.eventMapper = eventMapper;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/save")
    public EventDto save(@Valid @RequestBody EventDto eventDto) {
        return eventService.save(eventDto);
    }

    @PostMapping("/create-with-image")
    public ResponseEntity<String> createWithImage(
            @RequestPart("event") EventDto eventDto,
            @RequestPart("image") MultipartFile imageFile) {

        try {
            Event event = eventMapper.fromEventDTO(eventDto);

            if (eventDto.categoryId() != null) {
                Category category = categoryRepository.findById(eventDto.categoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("Catégorie introuvable"));
                event.setCategory(category);
            }

            String imagePath = imageService.saveResizedImage(imageFile, null);
            event.setImageUrl(imagePath);

            eventService.saveEntity(event);
            return ResponseEntity.ok("Événement créé avec image");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur image : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur événement : " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public EventDto update(@PathVariable Long id, @Valid @RequestBody EventDto eventDto) throws EventNotFound {
        return eventService.update(String.valueOf(id), eventDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws EventNotFound {
        eventService.deleteById(String.valueOf(id));
        return ResponseEntity.ok("Event deleted successfully");
    }

    @GetMapping("/all")
    public List<EventDto> getAllEvent() {
        return eventService.getAllEventDtos();
    }

    @GetMapping("/get/{id}")
    public EventDto get(@PathVariable Long id) throws EventNotFound {
        return eventService.getEventById(String.valueOf(id));
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            Event event = eventService.getEventByIdEntity(id);
            String imagePath = imageService.saveResizedImage(imageFile, Long.parseLong(String.valueOf(id)));
            event.setImageUrl(imagePath);
            eventService.saveEntity(event);
            return ResponseEntity.ok("Image uploaded and saved successfully.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors du traitement de l'image : " + e.getMessage());
        } catch (EventNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Événement non trouvé : " + e.getMessage());
        }
    }

    // endpoints for managing dates, locations, and seats

    @PostMapping("/{eventId}/dates")
    public ResponseEntity<EventDateDto> addDateToEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody EventDateDto dateDto) throws EventNotFound {
        EventDateDto savedDate = eventService.addDateToEvent(eventId, dateDto);
        return ResponseEntity.ok(savedDate);
    }

    @GetMapping("/{eventId}/dates")
    public ResponseEntity<List<EventDateDto>> getEventDates(
            @PathVariable Long eventId) throws EventNotFound {
        List<EventDateDto> dates = eventService.getEventDates(eventId);
        return ResponseEntity.ok(dates);
    }

    @PostMapping("/dates/{dateId}/locations")
    public ResponseEntity<EventLocationDto> addLocationToDate(
            @PathVariable Long dateId,
            @Valid @RequestBody EventLocationDto locationDto) {
        EventLocationDto savedLocation = eventService.addLocationToDate(dateId, locationDto);
        return ResponseEntity.ok(savedLocation);
    }

    @GetMapping("/dates/{dateId}/locations")
    public ResponseEntity<List<EventLocationDto>> getDateLocations(
            @PathVariable Long dateId) {
        List<EventLocationDto> locations = eventService.getDateLocations(dateId);
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/locations/{locationId}/seats")
    public ResponseEntity<EventSeatDto> addSeatToLocation(
            @PathVariable Long locationId,
            @Valid @RequestBody EventSeatDto seatDto) {
        EventSeatDto savedSeat = eventService.addSeatToLocation(locationId, seatDto);
        return ResponseEntity.ok(savedSeat);
    }

    @GetMapping("/locations/{locationId}/seats")
    public ResponseEntity<List<EventSeatDto>> getLocationSeats(
            @PathVariable Long locationId) {
        List<EventSeatDto> seats = eventService.getLocationSeats(locationId);
        return ResponseEntity.ok(seats);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}