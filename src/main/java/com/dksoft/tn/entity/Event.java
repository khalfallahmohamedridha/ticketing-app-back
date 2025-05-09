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
        private String type;

        @ManyToOne
        private Category category;

        @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<EventDate> dates = new ArrayList<>();

        @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Ticket> tickets;

        // Helper method to add a date
        public void addDate(EventDate date) {
                dates.add(date);
                date.setEvent(this);
        }

        // Helper method to remove a date
        public void removeDate(EventDate date) {
                dates.remove(date);
                date.setEvent(null);
        }
}