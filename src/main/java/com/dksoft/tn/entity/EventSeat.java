package com.dksoft.tn.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "event_seats")
public class EventSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String section;

    @Column
    private String row;

    @Column
    private String seatNumber;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "event_location_id")
    private EventLocation eventLocation;
}