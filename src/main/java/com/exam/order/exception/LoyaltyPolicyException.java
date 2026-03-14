package com.exam.order.exception;

public class LoyaltyPolicyException extends OrderProcessingException{
    public LoyaltyPolicyException(String message) {
        super(message);
    }
}
