package com.task.scraper.domain.flight.service;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.task.scraper.domain.flight.dto.FlightRequest;
import com.task.scraper.domain.flight.entity.Flight;
import com.task.scraper.domain.flight.repository.FlightRepository;
import com.task.scraper.global.common.scrape.ScrapeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {

    private final ScrapeManager scrapeManager;
    private final FlightRepository resultRepository;

    @Transactional
    public void scrapeInfo(FlightRequest flightRequest) {

        // 로그인 세션이 필요한 경우, 최초 1회 로그인
        Page page = scrapeManager.newPage();
        page.navigate("https://example.com/login");
        page.fill("#id", "user");
        page.fill("#pw", "pass");
        page.click("#login");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.close();

        log.info("공유 세션 로그인 완료");

        List<CompletableFuture<Flight>> futures = flightRequest.getAirlines().stream()
                .map(airline -> this.scrapeAsync(flightRequest, airline))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    @Async
    private CompletableFuture<Flight> scrapeAsync(FlightRequest orderRequest, String airline) {
        try {
            Page page = scrapeManager.newPage(); // 세션 공유됨

            // TODO: 실제 크롤링 URL 및 로직 작성
            String url = buildUrl(orderRequest, airline);
            page.navigate(url);

            // 예: 좌석 예약 가능 여부 판단 - CSS Selector 수정 필요
            boolean available = page.locator(".seat-available").count() > 0;

            page.close();

            Flight result = new Flight(orderRequest, airline, available);
            resultRepository.save(result);

            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

    private String buildUrl(FlightRequest orderRequest, String airline) {
        // 항공사별 예약 조회 URL 조합 예시 (실제에 맞게 변경)
        return String.format("https://example.com/%s/search?from=%s&to=%s&date=%s",
                airline, orderRequest.getOrigin(), orderRequest.getDest(), orderRequest.getDate().toString());
    }
}
