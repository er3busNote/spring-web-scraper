package com.task.scraper.domain.scrape.dto;

import com.task.scraper.global.common.validation.MinListSize;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScrapeRequest {

    @MinListSize(value = 1, message = "비행기정보는 최소 1개 이상의 항목을 포함해야 합니다.")
    private List<String> airlines;
    
    @NotBlank(message="출발공항코드가 존재하지 않습니다.")
    private String origin;

    @NotBlank(message="도착공항코드가 존재하지 않습니다.")
    private String dest;

    @NotNull(message="날짜정보가 존재하지 않습니다.")
    private LocalDate date;
}
