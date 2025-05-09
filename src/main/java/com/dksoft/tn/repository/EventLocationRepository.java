// EventLocationRepository.java
package com.dksoft.tn.repository;

import com.dksoft.tn.entity.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
    List<EventLocation> findByEventDateId(Long eventDateId);
}