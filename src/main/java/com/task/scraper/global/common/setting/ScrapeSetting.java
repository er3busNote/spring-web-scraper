package com.task.scraper.global.common.setting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "spring.scrape")
@Validated
@Getter @Setter
public class ScrapeSetting {

    @NotNull
    private Boolean proxy;
}
