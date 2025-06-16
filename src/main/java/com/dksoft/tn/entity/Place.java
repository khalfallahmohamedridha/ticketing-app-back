package com.dksoft.tn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    private Event event;



<<<<<<< Updated upstream
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();
=======
>>>>>>> Stashed changes
}