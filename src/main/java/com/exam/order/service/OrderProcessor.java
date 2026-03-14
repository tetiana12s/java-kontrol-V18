package com.exam.order.service;

import com.exam.order.exception.DatabaseException;
import com.exam.order.exception.OrderProcessingException;
import com.exam.order.exception.ValidationException;
import com.exam.order.model.Order;
import com.exam.order.payment.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

public abstract class OrderProcessor {
    protected static final Logger logger = LoggerFactory.getLogger(OrderProcessor.class);
    protected final PaymentMethod paymentMethod;

    protected OrderProcessor(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Template Method
    public final void process(Order order) {
        logger.info("--- Starting processing order #{} ---", order.getId());
        try {
            validate(order);
            double amount = calculateAmount(order); // VIP знижка
            applyLoyaltyPoints(order);
            paymentMethod.pay(amount);
            saveToDatabase(order);
            logger.info("--- Order #{} processed successfully ---", order.getId());
        } catch (DatabaseException e) {
            throw new OrderProcessingException("Infrastructure error", e);
        } catch (Exception e) {
            logger.error("Critical error during processing: {}", e.getMessage());
            throw e;
        }
    }

    protected void validate(Order order) {
        if (!order.getCustomerEmail().endsWith("@company.com")) {
            logger.warn("Validation failed for email: {}", order.getCustomerEmail());
            throw new ValidationException("Only @company.com emails are allowed!");
        }
    }

    protected abstract double calculateAmount(Order order);
    protected abstract void applyLoyaltyPoints(Order order);

    private void saveToDatabase(Order order) throws DatabaseException {
        if (order.getId() == null) {
            throw new DatabaseException("DB Error: Order ID is null", new RuntimeException("NullPointer"));
        }
    }

    public Optional<Order> findById(String id) {
        return Optional.empty();
    }
}
