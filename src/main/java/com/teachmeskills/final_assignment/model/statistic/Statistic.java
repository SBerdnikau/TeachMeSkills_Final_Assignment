package com.teachmeskills.final_assignment.model.statistic;

import com.teachmeskills.final_assignment.log.Logger;
import com.teachmeskills.final_assignment.model.check.CheckImpl;
import com.teachmeskills.final_assignment.model.invoice.InvoiceImpl;
import com.teachmeskills.final_assignment.model.order.OrderImpl;
import com.teachmeskills.final_assignment.utils.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is used to describe the result of file processing statistics
 * and output information to a file and to the console.
 */
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
        String statistic = Constants.HEAD_STATISTIC + Constants.TOTAL_AMOUNT_STATISTIC + Constants.DELIMITER_2 +
                String.format(Constants.CHECK_AMOUNT_STATISTIC, totalCheckAmount) +
                String.format(Constants.INVOICE_AMOUNT_STATISTIC, totalInvoiceAmount) +
                String.format(Constants.ORDER_AMOUNT_STATISTIC, totalOrderAmount) + Constants.DELIMITER_1;
        System.out.println(statistic);
    }

    public void writeStatistic() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PATH_TO_STATISTIC_FILE))) {
            writer.write(Constants.HEAD_STATISTIC + Constants.TOTAL_AMOUNT_STATISTIC + Constants.DELIMITER_2 +
                    String.format(Constants.CHECK_AMOUNT_STATISTIC, totalCheckAmount) +
                    String.format(Constants.INVOICE_AMOUNT_STATISTIC, totalInvoiceAmount) +
                    String.format(Constants.ORDER_AMOUNT_STATISTIC, totalOrderAmount) + Constants.DELIMITER_1);
            writer.newLine();
            Logger.logInfo(Constants.MESSAGE_FILE_RECORD_SUCCESS);
        } catch (IOException e) {
            Logger.logException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return Double.compare(totalCheckAmount, statistic.totalCheckAmount) == 0 &&
                Double.compare(totalInvoiceAmount, statistic.totalInvoiceAmount) == 0 &&
                Double.compare(totalOrderAmount, statistic.totalOrderAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCheckAmount, totalInvoiceAmount, totalOrderAmount);
    }
}