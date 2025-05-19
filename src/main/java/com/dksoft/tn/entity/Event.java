package com.dksoft.tn.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "events")
public class Event {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "image_url")
        private String imageUrl;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private String date;

        @Column(nullable = false)
        private String hour;

        @Column(nullable = false)
        private String location;

        @Column(nullable = false)
        private long price;

        @Column(nullable = false)
        private String type;

        @ManyToOne
        private Category category;

        @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference("event-ticket")
        private List<Ticket> tickets;

}
