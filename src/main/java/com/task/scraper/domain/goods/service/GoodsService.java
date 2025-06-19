package com.task.scraper.domain.goods.service;

import com.microsoft.playwright.Page;
import com.task.scraper.domain.goods.dto.response.GoodsResponse;
import com.task.scraper.domain.goods.entity.Goods;
import com.task.scraper.domain.goods.repository.GoodsRepository;
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
public class GoodsService {

    private final ScrapeManager scrapeManager;
    private final GoodsRepository goodsRepository;

    public void scrapeInfo() {
        Page page = scrapeManager.newPage();
        page.close();

        List<GoodsResponse> goodsResponses = this.findGoods();

        List<CompletableFuture<GoodsResponse>> futures = goodsResponses.stream()
                .map(this::scrapeAsync)
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    @Transactional
    private List<GoodsResponse> findGoods() {
        List<Goods> goods = this.goodsRepository.findAll();
        return goods.stream().map(GoodsResponse::of).collect(Collectors.toList());
    }

    @Async
    private CompletableFuture<GoodsResponse> scrapeAsync(GoodsResponse goods) {
        try {
            log.info(goods.getGoodsCode());
            return CompletableFuture.completedFuture(goods);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }
}
