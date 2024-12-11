package com.teachmeskills.final_assignment.model.invoice;

import com.teachmeskills.final_assignment.model.Document;

import java.util.Objects;

/**
 * This class is used to describe invoices files.
 */
public class InvoiceImpl implements Document {

    private double totalAmount;

    public InvoiceImpl() {
    }

    public InvoiceImpl(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceImpl invoice = (InvoiceImpl) o;
        return Double.compare(totalAmount, invoice.totalAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalAmount);
    }
}