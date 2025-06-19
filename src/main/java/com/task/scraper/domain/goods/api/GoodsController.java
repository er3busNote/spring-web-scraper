package com.task.scraper.domain.goods.api;

import com.task.scraper.domain.goods.service.GoodsService;
import com.task.scraper.global.common.dto.response.ResponseHandler;
import com.task.scraper.global.error.dto.response.ErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final ErrorHandler errorHandler;

    @PostMapping("/create")
    public ResponseEntity<ResponseHandler> createGoods() {
        goodsService.scrapeInfo();
        return ResponseEntity.created(URI.create("/create"))
                .body(ResponseHandler.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("상품정보 스크래핑이 실행되었습니다")
                        .build());
    }
}
