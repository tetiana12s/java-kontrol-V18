package com.exam.order.payment;

import com.exam.order.exception.OrderProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayPalPayment implements PaymentMethod {
    private static final Logger log = LoggerFactory.getLogger(PayPalPayment.class);
    public void pay(double amount) {
        if (amount < 600) throw new OrderProcessingException("PayPal min amount is 600!");
        log.info("Successfully paid {} via PayPal", amount);
    }
}
