package com.task.scraper.scrape;

import com.task.scraper.common.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScrapeTest extends BaseTest {

    @Test
    @DisplayName("상품정보 스크래핑 테스트")
    public void GoodsScrapeTest() throws Exception {
        mockMvc.perform(post("/goods/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("항공사 스크래핑 테스트")
    public void FlightScrapeTest() throws Exception {
        mockMvc.perform(post("/flight/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());;
    }
}
