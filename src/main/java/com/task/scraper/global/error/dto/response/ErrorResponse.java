package com.task.scraper.global.error.dto.response;

import com.task.scraper.global.error.dto.ErrorInfo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private String message;
    private String code;
    private int status;
    private ErrorInfo errors;
}
