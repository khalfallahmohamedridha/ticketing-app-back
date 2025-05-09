package com.dksoft.tn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "event_locations")
public class EventLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "event_date_id")
    private EventDate eventDate;

    @OneToMany(mappedBy = "eventLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventSeat> seats = new ArrayList<>();
}