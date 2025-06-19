package com.task.scraper.domain.goods.dto;

import com.task.scraper.domain.goods.entity.GoodsImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {

    private Integer goodsImageAttachFile;
    private String goodsImageExtension;
    private Character basicImageYesNo;
    private Integer sortSequence;
    private Character useYesNo;

    public static ImageInfo of(GoodsImage goodsImage) {
        return ImageInfo.builder()
                .goodsImageAttachFile(goodsImage.getGoodsImageAttachFile())
                .goodsImageExtension(goodsImage.getGoodsImageExtension())
                .basicImageYesNo(goodsImage.getBasicImageYesNo())
                .sortSequence(goodsImage.getSortSequence())
                .useYesNo(goodsImage.getUseYesNo())
                .build();
    }
}
