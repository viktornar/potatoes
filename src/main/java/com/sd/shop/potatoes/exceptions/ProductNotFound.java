package com.sd.shop.potatoes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFound extends Exception {
    public ProductNotFound(String message) {
        super(message);
    }
}
