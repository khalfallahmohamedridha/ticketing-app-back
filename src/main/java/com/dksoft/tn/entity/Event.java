package com.dksoft.tn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

//        @ElementCollection
//        @CollectionTable(name = "event_images", joinColumns = @JoinColumn(name = "event_id"))
//        @Column(name = "image_url")
//        private List<String> imageUrls = new ArrayList<>();

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, length = 1000)
        private String description;

        @Column(nullable = false, length = 255)
        private String shortDescription;

<<<<<<< Updated upstream
        @Column(nullable = false)
        private boolean isActive = true;

        @ManyToOne
        @JoinColumn(name = "place_id", nullable = false)
        private Place place;
=======
        @Column(nullable = false, length = 255)
        private String tag;
>>>>>>> Stashed changes




        @ManyToOne
        private Category category;

        @ManyToOne
        @JoinColumn(name = "organizer_id", nullable = false)
        private User organizer;




        @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Ticket> ticket = new ArrayList<>();

        @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Place> places = new ArrayList<>();


        @Column(nullable = false, updatable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private LocalDate dateDebut;

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private LocalDate dateFin;




<<<<<<< Updated upstream
        public void setIsActive(boolean active) {
                this.isActive = active;
        }
=======
>>>>>>> Stashed changes
}