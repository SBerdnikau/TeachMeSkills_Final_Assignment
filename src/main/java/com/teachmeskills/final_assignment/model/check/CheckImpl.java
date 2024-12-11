package com.teachmeskills.final_assignment.model.check;

import com.teachmeskills.final_assignment.model.Document;

import java.util.Objects;

/**
 * This class is used to describe check files.
 */
public class CheckImpl implements Document {

    private double totalAmount;

    public CheckImpl() {
    }

    public CheckImpl(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckImpl check = (CheckImpl) o;
        return Double.compare(totalAmount, check.totalAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalAmount);
    }
}
