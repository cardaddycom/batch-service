package com.cardaddy.batch.exception;

public class MissingLocationException extends RuntimeException {

    public MissingLocationException(String errorMessage) {
        super(errorMessage);
    }

}
