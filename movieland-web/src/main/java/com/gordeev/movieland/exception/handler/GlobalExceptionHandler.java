package com.gordeev.movieland.exception.handler;

import com.gordeev.movieland.exception.AuthRequiredException;
import com.gordeev.movieland.vo.ExceptionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleInvalidRequest(Exception exception, WebRequest request) {
        log.warn("Request failed with exception: ", exception);

        ExceptionVO exceptionVO = new ExceptionVO();
        exceptionVO.setTitle("Not valid request parameters");
        exceptionVO.setMessage(exception.getMessage());

        return handleExceptionInternal(exception, exceptionVO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<Object> handleAuthRequired(Exception exception, WebRequest request) {
        log.warn("Request failed. Provided UUID is not valid: ", exception);

        ExceptionVO exceptionVO = new ExceptionVO();
        exceptionVO.setTitle("Authentication required");
        exceptionVO.setMessage(exception.getMessage());

        return handleExceptionInternal(exception, exceptionVO, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughtException(Exception exception, WebRequest request) {
        log.error("Request failed with internal error exception: ", exception);

        ExceptionVO exceptionVO = new ExceptionVO();
        exceptionVO.setTitle("Internal server error");
        exceptionVO.setMessage(exception.getMessage());

        return handleExceptionInternal(exception, exceptionVO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
