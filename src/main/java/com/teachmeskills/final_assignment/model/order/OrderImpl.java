package com.teachmeskills.final_assignment.model.order;

import com.teachmeskills.final_assignment.model.Document;

import java.util.Objects;

/**
 * This class is used to describe orders files.
 */
public class OrderImpl implements Document {

    private double totalAmount;

    public OrderImpl() {
    }

    public OrderImpl(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderImpl order = (OrderImpl) o;
        return Double.compare(totalAmount, order.totalAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalAmount);
    }
}
