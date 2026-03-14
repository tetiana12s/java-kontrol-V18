package com.exam.order.exception;

public class ValidationException extends OrderProcessingException {
    public ValidationException(String message) {
        super(message);
    }
}
