package com.teachmeskills.final_assignment.model.invoice;

public class Invoice {

    public static int countInvoice;

    private final double totalAmount;

    public Invoice(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
