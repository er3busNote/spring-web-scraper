package com.task.scraper.domain.flight.api;

import com.task.scraper.domain.flight.dto.FlightRequest;
import com.task.scraper.domain.flight.service.FlightService;
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
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/create")
    public ResponseEntity<ResponseHandler> createFlight(
            @RequestBody @Valid FlightRequest flightRequest
    ) {
        flightService.scrapeInfo(flightRequest);
        return ResponseEntity.created(URI.create("/create"))
                .body(ResponseHandler.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("항공사 스크래핑이 실행되었습니다")
                        .build());
    }
}
