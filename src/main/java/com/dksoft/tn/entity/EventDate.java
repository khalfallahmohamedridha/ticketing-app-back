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
@Table(name = "event_dates")
public class EventDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String hour;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "eventDate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventLocation> locations = new ArrayList<>();
}