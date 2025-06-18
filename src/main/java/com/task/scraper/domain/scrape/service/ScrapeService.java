package com.task.scraper.domain.scrape.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Proxy;
import com.task.scraper.domain.scrape.dto.ScrapeRequest;
import com.task.scraper.domain.scrape.entity.Scrape;
import com.task.scraper.domain.scrape.repository.ScrapeRepository;
import com.task.scraper.global.common.utils.ProxyPool;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ScrapeService {

    private final ProxyPool proxyPool;
    private final ScrapeRepository resultRepository;

    @Async
    public CompletableFuture<Scrape> scrapeAsync(ScrapeRequest orderRequest, String airline) {
        String proxy = proxyPool.getNextProxy();

        try (Playwright playwright = Playwright.create()) {
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
            options.setHeadless(true);
            options.setProxy(new Proxy(proxy));

            Browser browser = playwright.chromium().launch(options);
            Page page = browser.newPage();

            // TODO: 실제 크롤링 URL 및 로직 작성
            String url = buildUrl(orderRequest, airline);
            page.navigate(url);

            // 예: 좌석 예약 가능 여부 판단 - CSS Selector 수정 필요
            boolean available = page.locator(".seat-available").count() > 0;

            browser.close();

            Scrape result = new Scrape(orderRequest, airline, available, proxy);
            resultRepository.save(result);

            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(null);
        }
    }

    private String buildUrl(ScrapeRequest orderRequest, String airline) {
        // 항공사별 예약 조회 URL 조합 예시 (실제에 맞게 변경)
        return String.format("https://example.com/%s/search?from=%s&to=%s&date=%s",
                airline, orderRequest.getOrigin(), orderRequest.getDest(), orderRequest.getDate().toString());
    }
}
