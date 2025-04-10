package com.usareboot.back.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class ImageProcessingException extends RuntimeException {
    public ImageProcessingException(String message) {
        super(message);
    }

    public ImageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
