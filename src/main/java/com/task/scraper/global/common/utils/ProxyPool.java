package com.task.scraper.global.common.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ProxyPool {

    private final List<String> proxyList = Arrays.asList(
            "http://proxy1:port",
            "http://proxy2:port",
            "http://proxy3:port"
    );

    private final AtomicInteger idx = new AtomicInteger(0);

    // 순환 방식으로 proxy 할당
    public synchronized String getNextProxy() {
        int i = idx.getAndUpdate(x -> (x + 1) % proxyList.size());
        return proxyList.get(i);
    }
}
