package com.exam.order.model;

import com.exam.order.exception.ValidationException;
import java.util.Arrays;

public class Order {
    private final String id;
    private final String customerEmail;
    private final OrderItem[] items;
    private final boolean isVip;

    public Order(String id, String email) {
        this(id, email, new OrderItem[0], false);
    }

    public Order(String id, String email, OrderItem[] items, boolean isVip) {

        if (email == null || !email.contains("@company.com")) {
            throw new ValidationException("Email must be a corporate address (@company.com)");
        }

        this.id = id;
        this.customerEmail = email;
        this.isVip = isVip;
        this.items = (items != null) ? Arrays.copyOf(items, items.length) : new OrderItem[0];
    }

    public String getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    public boolean isVip() { return isVip; }

    // Defensive copy
    public OrderItem[] getItems() {
        return Arrays.copyOf(items, items.length);
    }

    public double calculateSubtotal() {
        return Arrays.stream(items).mapToDouble(OrderItem::getPrice).sum();
    }

    private OrderStatus status = OrderStatus.NEW;

    public void setStatus(OrderStatus newStatus) {
        if (newStatus == OrderStatus.CANCELLED) {
            if (this.status != OrderStatus.NEW && this.status != OrderStatus.PAID) {
                throw new IllegalStateException("Cannot cancel order from status: " + this.status);
            }
        }
        this.status = newStatus;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
