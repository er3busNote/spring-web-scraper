package com.task.scraper.domain.flight.repository;

import com.task.scraper.domain.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
