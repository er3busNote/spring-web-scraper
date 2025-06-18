package com.task.scraper.scrape;

import com.task.scraper.common.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScrapeTest extends BaseTest {

    @Test
    @DisplayName("스크래핑 확인")
    public void CsrfForbidden() throws Exception {

        mockMvc.perform(post("/scrape/create")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}
