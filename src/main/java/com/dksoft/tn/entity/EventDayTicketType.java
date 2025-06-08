package com.dksoft.tn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "event_day_ticket_types")
public class EventDayTicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_day_id", nullable = false)
    private EventDay eventDay;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    @Column(nullable = false)
    private int maxNumber;
}