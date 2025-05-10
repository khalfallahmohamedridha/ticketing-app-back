package com.dksoft.tn.entity;

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
        private Category category;

        @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Ticket> tickets;

}
