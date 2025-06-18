package com.task.scraper.domain.scrape.api;

import com.task.scraper.domain.scrape.dto.ScrapeRequest;
import com.task.scraper.domain.scrape.service.ScrapeService;
import com.task.scraper.global.common.dto.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scrape")
public class ScrapeController {

    private final ScrapeService scrapeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseHandler> createScrape(
            @RequestBody @Valid ScrapeRequest orderRequest
    ) {
        for (String airline : orderRequest.getAirlines()) {
            scrapeService.scrapeAsync(orderRequest, airline);
        }
        return ResponseEntity.created(URI.create("/create"))
                .body(ResponseHandler.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("스크래핑이 실행되었습니다")
                        .build());
    }
}
