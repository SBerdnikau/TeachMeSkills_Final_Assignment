package com.teachmeskills.final_assignment.model.statistic;

import com.teachmeskills.final_assignment.exception.InvalidWriteFileException;
import com.teachmeskills.final_assignment.log.Logger;
import com.teachmeskills.final_assignment.model.check.CheckImpl;
import com.teachmeskills.final_assignment.model.invoice.InvoiceImpl;
import com.teachmeskills.final_assignment.model.order.OrderImpl;
import com.teachmeskills.final_assignment.utils.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Statistic {

    private double totalCheckAmount;
    private double totalInvoiceAmount;
    private double totalOrderAmount;

    public void addCheck(CheckImpl check) {
        totalCheckAmount += check.getTotalAmount();
    }

    public void addInvoice(InvoiceImpl invoice) {
        totalInvoiceAmount += invoice.getTotalAmount();
    }

    public void addOrder(OrderImpl order) {
        totalOrderAmount += order.getTotalAmount();
    }

    public void printStatistics() {
        String statistic = String.format(Constants.HEAD_STATISTIC) +
                String.format("\nType\t\t\t\t\t|\tTotal amount\n") +
                String.format(Constants.DELIMITER_2) +
                String.format("\nTotal Check Amount:\t\t|\t%.2f", totalCheckAmount) +
                String.format("\nTotal Invoice Amount:\t|\t%.2f", totalInvoiceAmount) +
                String.format("\nTotal Order Amount:\t\t|\t%.2f\n", totalOrderAmount) +
                String.format(Constants.DELIMITER_1);
        System.out.println(statistic);
    }

    public void writeStatistic() throws InvalidWriteFileException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PATH_TO_STATISTIC_FILE))) {
            writer.write(String.format(Constants.HEAD_STATISTIC) +
                    String.format("\nType\t\t\t\t\t|\tTotal amount\n") +
                    String.format(Constants.DELIMITER_2) +
                    String.format("\nTotal Check Amount:\t\t|\t%.2f", totalCheckAmount) +
                    String.format("\nTotal Invoice Amount:\t|\t%.2f", totalInvoiceAmount) +
                    String.format("\nTotal Order Amount:\t\t|\t%.2f\n", totalOrderAmount) +
                    String.format(Constants.DELIMITER_1));
            writer.newLine();
            Logger.logInfo("The file is was recorded successful");
        } catch (IOException e) {
            Logger.logException(e);
            throw new InvalidWriteFileException("File statistic not was recorded\t" , Constants.ERROR_CODE_STATISTIC);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return Double.compare(totalCheckAmount, statistic.totalCheckAmount) == 0 && Double.compare(totalInvoiceAmount, statistic.totalInvoiceAmount) == 0 && Double.compare(totalOrderAmount, statistic.totalOrderAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCheckAmount, totalInvoiceAmount, totalOrderAmount);
    }

}