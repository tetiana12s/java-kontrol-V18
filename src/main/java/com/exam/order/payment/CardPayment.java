package com.exam.order.payment;

import com.exam.order.exception.OrderProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardPayment implements PaymentMethod {
    private static final Logger log = LoggerFactory.getLogger(CardPayment.class);
    public void pay(double amount) {
        if (amount > 45000) throw new OrderProcessingException("Card limit 45000 exceeded!");
        log.info("Successfully paid {} via Card", amount);
    }
}
