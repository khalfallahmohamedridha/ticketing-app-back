package com.dksoft.tn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
        private String imageUrl; // 🔹 Champ ajouté pour stocker le chemin/URL de la photo

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private String date;

        @Column(nullable = false)
        private String hour;

        @Column(nullable = false)
        private String place;

        @Column(nullable = false)
        private long price;

        @Column(nullable = false)
        private String type;

        @ManyToOne
        @JsonBackReference
        private Category category;

        @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Ticket> tickets;

}
