package com.dksoft.tn.service;

import com.dksoft.tn.dto.TicketDto;
import com.dksoft.tn.entity.Cart;
import com.dksoft.tn.entity.Event;
import com.dksoft.tn.entity.Ticket;
import com.dksoft.tn.exception.TicketNotFoundException;
import com.dksoft.tn.mapper.TicketMapper;
import com.dksoft.tn.repository.EventRepository;
import com.dksoft.tn.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;
    private final EventRepository eventRepository;

    public TicketService(TicketRepository ticketRepository, TicketMapper mapper, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
        this.eventRepository = eventRepository;
    }

    public TicketDto save(TicketDto ticketDto) {
        Event event = eventRepository.findById(ticketDto.eventId())
                .orElseThrow(() -> new TicketNotFoundException("Event with id = '" + ticketDto.eventId() + "' not found."));
        Ticket ticket = mapper.fromTicketDto(ticketDto);
        ticket.setEvent(event);
        Ticket ticketSaved = ticketRepository.save(ticket);
        return mapper.fromTicket(ticketSaved);
    }

    public TicketDto update(Long id, TicketDto ticketDto) throws TicketNotFoundException {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id = '" + id + "' not found."));
        updateTicketFields(ticket, ticketDto);
        Ticket ticketUpdate = ticketRepository.save(ticket);
        log.info("Ticket updated successfully with ID: {}", id);
        return mapper.fromTicket(ticketUpdate);
    }

    private void updateTicketFields(Ticket ticket, TicketDto ticketDto) {
        ticket.setNumTicket(ticketDto.numTicket());
        ticket.setPlace(ticketDto.place());
        if (ticketDto.eventId() != null) {
            Event event = eventRepository.findById(ticketDto.eventId())
                    .orElseThrow(() -> new TicketNotFoundException("Event with id = '" + ticketDto.eventId() + "' not found."));
            ticket.setEvent(event);
        }
        if (ticketDto.cartId() != null) {
            Cart cart = new Cart();
            cart.setId(ticketDto.cartId());
            ticket.setCart(cart);
        } else {
            ticket.setCart(null);
        }
    }

    public void deleteById(long id) {
        ticketRepository.deleteById(id);
        log.info("Ticket deleted successfully with ID: {}", id);
    }

    public List<Ticket> getAllTicket() {
        return ticketRepository.findAll();
    }

    public TicketDto getTicketById(long id) throws TicketNotFoundException {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id = '" + id + "' not found."));
        return mapper.fromTicket(ticket);
    }

    public List<TicketDto> getAllTicketDtos() {
        return ticketRepository.findAll()
                .stream()
                .map(mapper::fromTicket)
                .toList();
    }
}