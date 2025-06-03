package com.dksoft.tn.controller;

import com.dksoft.tn.dto.TicketDto;
import com.dksoft.tn.entity.Ticket;
import com.dksoft.tn.exception.TicketNotFoundException;
import com.dksoft.tn.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @PostMapping("/save")
    public TicketDto save(@RequestBody TicketDto ticketDto) {
        return ticketService.save(ticketDto);
    }
    @PutMapping("/update/{id}")
    public TicketDto update(@PathVariable long id, @RequestBody TicketDto ticketDto) throws TicketNotFoundException {
        return ticketService.update(id, ticketDto);
    }

    /*@PreAuthorize("hasRole('ADMIN')")*/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws TicketNotFoundException {
        ticketService.deleteById(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @GetMapping("/all")
    public List<Ticket> getAllTicket() {
        return ticketService.getAllTicket();
    }

    @GetMapping("/get/{id}")
    public TicketDto get(@PathVariable long id) throws TicketNotFoundException {
        return ticketService.getTicketById(id);
    }
}
