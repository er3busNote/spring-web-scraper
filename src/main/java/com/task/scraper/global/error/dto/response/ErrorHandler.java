package com.task.scraper.global.error.dto.response;

import com.task.scraper.global.error.dto.ErrorCode;
import com.task.scraper.global.error.dto.ErrorInfo;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler {

    public ErrorResponse buildError(ErrorCode errorCode, ErrorInfo errorMessage) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(errorMessage)
                .build();
    }
}
