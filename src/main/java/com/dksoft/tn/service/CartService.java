package com.dksoft.tn.service;

import com.dksoft.tn.dto.TicketDto;
import com.dksoft.tn.entity.Cart;
import com.dksoft.tn.entity.Ticket;
import com.dksoft.tn.exception.CartNotFoundException;
import com.dksoft.tn.exception.TicketNotFoundException;
import com.dksoft.tn.mapper.TicketMapper;
import com.dksoft.tn.repository.CartRepository;
import com.dksoft.tn.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public CartService(CartRepository cartRepository, TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.cartRepository = cartRepository;
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    // Crée un nouveau panier
    public Cart createCart() {
        Cart cart = new Cart();
        cart = cartRepository.save(cart);
        log.info("Panier créé avec ID: {}", cart.getId());
        return cart;
    }

    // Récupère un panier par ID
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Panier avec id = '" + id + "' non trouvé."));
    }

    // Ajoute un ticket au panier
    public void addTicketToCart(Long cartId, Long ticketId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Panier avec id = '" + cartId + "' non trouvé."));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket avec id = '" + ticketId + "' non trouvé."));

        // Vérifie si le ticket est déjà dans le panier
        if (cart.getTickets().contains(ticket)) {
            throw new IllegalStateException("Le ticket est déjà dans le panier.");
        }

        // Associe le ticket au panier
        ticket.setCart(cart);
        cart.getTickets().add(ticket);
        cartRepository.save(cart);
        log.info("Ticket {} ajouté au panier {}", ticketId, cartId);
    }

    // Supprime un ticket du panier
    public void removeTicketFromCart(Long cartId, Long ticketId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Panier avec id = '" + cartId + "' non trouvé."));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket avec id = '" + ticketId + "' non trouvé."));

        // Dissocie le ticket du panier
        cart.getTickets().remove(ticket);
        ticket.setCart(null);
        cartRepository.save(cart);
        log.info("Ticket {} supprimé du panier {}", ticketId, cartId);
    }

    // Vide le panier
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Panier avec id = '" + cartId + "' non trouvé."));
        cart.getTickets().forEach(ticket -> ticket.setCart(null));
        cart.getTickets().clear();
        cartRepository.save(cart);
        log.info("Panier {} vidé", cartId);
    }

    // Récupère les tickets d'un panier sous forme de DTO
    public List<TicketDto> getCartTickets(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Panier avec id = '" + cartId + "' non trouvé."));
        return cart.getTickets().stream()
                .map(ticketMapper::fromTicket)
                .collect(Collectors.toList());
    }
}