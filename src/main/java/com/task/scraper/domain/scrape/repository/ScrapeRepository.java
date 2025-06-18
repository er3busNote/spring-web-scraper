package com.task.scraper.domain.scrape.repository;

import com.task.scraper.domain.scrape.entity.Scrape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapeRepository extends JpaRepository<Scrape, Long> {
}
