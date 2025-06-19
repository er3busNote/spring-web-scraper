package com.task.scraper.global.common.scrape;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Proxy;
import com.task.scraper.global.common.setting.ScrapeSetting;
import com.task.scraper.global.common.utils.ProxyPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@RequiredArgsConstructor
public class ScrapeManager {

    private final ProxyPool proxyPool;
    private final ScrapeSetting scrapeSetting;

    private Browser browser;
    private BrowserContext browserContext;

    @PostConstruct
    public void init() {
        String proxy = proxyPool.getNextProxy();
        Playwright playwright = Playwright.create();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setHeadless(true);
        if (scrapeSetting.getProxy()) {
            options.setProxy(new Proxy(proxy));
        }

        browser = playwright.chromium().launch(options);

        browserContext = browser.newContext();
    }

    public Page newPage() {
        return browserContext.newPage();
    }

    @PreDestroy
    public void cleanup() {
        browserContext.close();
        browser.close();
    }
}
