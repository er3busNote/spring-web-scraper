package com.task.scraper.domain.goods.dto.response;

import com.task.scraper.domain.goods.dto.ImageInfo;
import com.task.scraper.domain.goods.entity.Goods;
import com.task.scraper.domain.goods.entity.Price;
import com.task.scraper.domain.goods.type.OptionsType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsResponse {

    protected String goodsCode;
    protected String goodsName;
    protected String goodsBrand;
    protected String goodsGender;
    protected String goodsYear;
    protected BigDecimal supplyPrice;
    protected BigDecimal salePrice;
    protected List<ImageInfo> imageList;
    protected List<String> keywordList;

    public static GoodsResponse of(Goods goods) {
        return GoodsResponse.builder()
                .goodsCode(goods.getGoodsCode())
                .goodsName(goods.getGoodsName())
                .goodsBrand(goods.getGoodsBrand())
                .goodsGender(goods.getGoodsGender())
                .goodsYear(goods.getGoodsYear())
                .supplyPrice(goods.getGoodsPrices().stream()
                        .filter(it -> it.getOptions().getOptionsType().equals(OptionsType.BASIC))
                        .map(Price::getSupplyPrice)
                        .findFirst().orElse(null))
                .salePrice(goods.getGoodsPrices().stream()
                        .filter(it -> it.getOptions().getOptionsType().equals(OptionsType.BASIC))
                        .map(Price::getSalePrice)
                        .findFirst().orElse(null))
                .imageList(goods.getGoodsImages().stream()
                        .filter(image -> image.getBasicImageYesNo() == 'Y')   // innerJoin 할때, 조건을 추가로 붙여야 함..
                        .map(ImageInfo::of)
                        .collect(Collectors.toList()))
                .keywordList(getKeywordList(goods.getKeyword()))
                .build();
    }

    public static List<String> getKeywordList(String keyword) {
        return Optional.ofNullable(keyword)
                .map(s -> Arrays.stream(s.split("#"))
                        .filter(token -> !token.isEmpty())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
