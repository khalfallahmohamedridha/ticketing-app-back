// EventDateRepository.java
package com.dksoft.tn.repository;

import com.dksoft.tn.entity.EventDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDateRepository extends JpaRepository<EventDate, Long> {
    List<EventDate> findByEventId(Long eventId);
}