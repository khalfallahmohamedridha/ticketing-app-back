// EventSeatRepository.java
package com.dksoft.tn.repository;

import com.dksoft.tn.entity.EventSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventSeatRepository extends JpaRepository<EventSeat, Long> {
    List<EventSeat> findByEventLocationId(Long eventLocationId);
}