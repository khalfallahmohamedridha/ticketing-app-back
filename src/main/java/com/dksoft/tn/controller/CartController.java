package com.dksoft.tn.controller;

import com.dksoft.tn.dto.TicketDto;
import com.dksoft.tn.entity.Cart;
import com.dksoft.tn.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Crée un nouveau panier
    @PostMapping("/create")
    public ResponseEntity<Cart> createCart() {
        Cart cart = cartService.createCart();
        return ResponseEntity.ok(cart);
    }

    // Récupère un panier par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }

    // Ajoute un ticket au panier
    @PostMapping("/{cartId}/tickets/{ticketId}")
    public ResponseEntity<String> addTicketToCart(@PathVariable Long cartId, @PathVariable Long ticketId) {
        cartService.addTicketToCart(cartId, ticketId);
        return ResponseEntity.ok("Ticket ajouté au panier avec succès");
    }

    // Supprime un ticket du panier
    @DeleteMapping("/{cartId}/tickets/{ticketId}")
    public ResponseEntity<String> removeTicketFromCart(@PathVariable Long cartId, @PathVariable Long ticketId) {
        cartService.removeTicketFromCart(cartId, ticketId);
        return ResponseEntity.ok("Ticket supprimé du panier avec succès");
    }

    // Vide le panier
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok("Panier vidé avec succès");
    }

    // Récupère les tickets d'un panier
    @GetMapping("/{cartId}/tickets")
    public ResponseEntity<List<TicketDto>> getCartTickets(@PathVariable Long cartId) {
        List<TicketDto> tickets = cartService.getCartTickets(cartId);
        return ResponseEntity.ok(tickets);
    }
}