package com.sd.shop.potatoes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ImageConflict extends Exception {
    public ImageConflict(String message) {
        super(message);
    }
}
