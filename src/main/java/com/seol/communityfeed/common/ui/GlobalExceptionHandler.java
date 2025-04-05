package com.seol.communityfeed.common.ui;

import com.seol.communityfeed.common.domain.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Response<Void> handleIllegalArgumentException(IllegalArgumentException exception){
        log.error("IllegalArgumentException: {}", exception.getMessage());
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();

        // Swagger, HTML, JS, CSS 요청은 기본 처리로 넘김
        if (uri.startsWith("/v3/api-docs") ||
                uri.startsWith("/swagger-ui") ||
                uri.endsWith(".html") ||
                uri.endsWith(".js") ||
                uri.endsWith(".css")) {
            throw e;
        }

        log.error("Exception: {}", e.getMessage());
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }
}
