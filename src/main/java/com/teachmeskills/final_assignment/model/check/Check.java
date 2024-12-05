package com.teachmeskills.final_assignment.model.check;

public class Check {

    public static int countCheck;

    private final double totalAmount;

    public Check(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

}
