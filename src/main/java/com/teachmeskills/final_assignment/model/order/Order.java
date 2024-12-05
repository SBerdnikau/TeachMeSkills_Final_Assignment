package com.teachmeskills.final_assignment.model.order;

public class Order {

    public static int countOrder;

    private final double totalAmount;

    public Order(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
