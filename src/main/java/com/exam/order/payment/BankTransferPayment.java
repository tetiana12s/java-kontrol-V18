package com.exam.order.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankTransferPayment implements PaymentMethod {
    private static final Logger log = LoggerFactory.getLogger(BankTransferPayment.class);
    public void pay(double amount) {
        double fee;
        if (amount >= 50000) {
            fee = 0.005; // 0.5% комісія
        } else {
            fee = 0.02;  // 2% комісія
        }
        double totalWithFee = amount + (amount * fee);
        log.info("Bank transfer: amount {}, fee {}%, total {}", amount, fee * 100, totalWithFee);
    }
}
