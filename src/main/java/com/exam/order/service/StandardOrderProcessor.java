package com.exam.order.service;

import com.exam.order.exception.LoyaltyPolicyException;
import com.exam.order.model.Order;
import com.exam.order.payment.PaymentMethod;

public class StandardOrderProcessor extends OrderProcessor {

    public StandardOrderProcessor(PaymentMethod paymentMethod) {
        super(paymentMethod);
    }

    @Override
    protected double calculateAmount(Order order) {
        double total = order.calculateSubtotal();
        if (order.isVip()) {
            logger.info("Applying 7% VIP discount for order #{}", order.getId());
            total *= 0.93;
        }
        return total;
    }

    @Override
    protected void applyLoyaltyPoints(Order order) {
        if (order.calculateSubtotal() < 0) {
            throw new LoyaltyPolicyException("Loyalty points cannot be applied to negative total!");
        }
        logger.info("Loyalty points calculated for order #{}", order.getId());
    }
}
