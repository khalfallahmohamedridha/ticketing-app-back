package com.dksoft.tn.repository;

import com.dksoft.tn.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
