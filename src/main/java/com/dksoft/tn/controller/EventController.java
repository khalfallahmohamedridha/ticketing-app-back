package com.dksoft.tn.controller;

import com.dksoft.tn.dto.EventDto;
import com.dksoft.tn.entity.Category;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.exception.CategoryNotFoundException;
import com.dksoft.tn.exception.EventNotFound;
import com.dksoft.tn.mapper.EventMapper;
import com.dksoft.tn.repository.CategoryRepository;
import com.dksoft.tn.service.EventService;
import com.dksoft.tn.service.ImageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.access.prepost.PreAuthorize;*/
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/events")
@PreAuthorize("hasRole('ADMIN')")
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
    /*@PreAuthorize("hasRole('ADMIN')")*/
    @PostMapping("/save")
    public EventDto save(@RequestBody EventDto eventDto) {
        return eventService.save(eventDto);
    }
    /*@PreAuthorize("hasRole('ADMIN')")*/
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
    /*@PreAuthorize("hasRole('ADMIN')")*/
    @PutMapping("/update/{id}")
    public EventDto update(@PathVariable Long id, @RequestBody EventDto eventDto) throws EventNotFound {
        return eventService.update(String.valueOf(id), eventDto);
    }
    /*@PreAuthorize("hasRole('ADMIN')")*/
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
    /*@PreAuthorize("hasRole('ADMIN')")*/
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
}
