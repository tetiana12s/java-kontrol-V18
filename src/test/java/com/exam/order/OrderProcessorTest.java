package com.exam.order;

import com.exam.order.model.Order;
import com.exam.order.model.OrderItem;
import com.exam.order.model.OrderStatus;
import com.exam.order.payment.*;
import com.exam.order.service.*;
import com.exam.order.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {
    private StandardOrderProcessor processor;

    @BeforeEach
    void setUp() {
        // PayPal як дефолтний метод для тестів
        processor = new StandardOrderProcessor(new PayPalPayment());
    }

    @Test
    void testSuccessProcess() {
        Order order = new Order("1", "user@company.com", new OrderItem[]{new OrderItem("Java", 1000)}, false);
        assertDoesNotThrow(() -> processor.process(order));
    }


    @ParameterizedTest
    @ValueSource(strings = {"admin@company.com", "dev@company.com", "ceo@company.com"})
    void testCorporateEmails(String email) {
        Order order = new Order("2", email, new OrderItem[]{new OrderItem("License", 700)}, false);
        assertDoesNotThrow(() -> processor.process(order));
    }


    @Test
    void testInvalidEmail() {
        assertThrows(ValidationException.class, () -> {
            new Order("3", "test@gmail.com");
        });
    }


    @Test
    void testPayPalLimit() {
        Order order = new Order("4", "work@company.com", new OrderItem[]{new OrderItem("Pen", 100)}, false);
        assertThrows(OrderProcessingException.class, () -> processor.process(order));
    }


    @Test
    void testCardLimit() {
        StandardOrderProcessor cardProc = new StandardOrderProcessor(new CardPayment());
        Order order = new Order("5", "manager@company.com", new OrderItem[]{new OrderItem("Luxury Car", 50000)}, false);
        assertThrows(OrderProcessingException.class, () -> cardProc.process(order));
    }


    @Test
    void testLoyaltyException() {
        Order order = new Order("6", "vip@company.com", new OrderItem[]{new OrderItem("Error", -100)}, true);
        assertThrows(OrderProcessingException.class, () -> processor.process(order));
    }

    // Перевірка VIP знижки 7%
    @Test
    void testVipDiscount() {
        Order order = new Order("7", "vip@company.com", new OrderItem[]{new OrderItem("Laptop", 10000)}, true);
        assertDoesNotThrow(() -> processor.process(order));
    }


    @Test
    void testInvalidStateTransition() {
        Order order = new Order("8", "test@company.com");
        order.setStatus(OrderStatus.CANCELLED); // Це можна (з NEW)
        assertThrows(IllegalStateException.class, () -> order.setStatus(OrderStatus.CANCELLED));
    }


    @Test
    void testDefensiveCopy() {
        OrderItem[] items = {new OrderItem("A", 100)};
        Order order = new Order("9", "a@company.com", items, false);
        items[0] = new OrderItem("B", 500); // Змінюємо вхідний масив
        assertNotEquals("B", order.getItems()[0].getName());
    }


    @Test
    void testOptionalMethod() {
        assertTrue(processor.findById("any").isEmpty());
    }
}
