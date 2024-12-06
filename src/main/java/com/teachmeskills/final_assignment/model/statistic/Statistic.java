package com.teachmeskills.final_assignment.model.statistic;

import com.teachmeskills.final_assignment.exception.WrongStatisticException;
import com.teachmeskills.final_assignment.log.LoggerService;
import com.teachmeskills.final_assignment.model.check.Check;
import com.teachmeskills.final_assignment.model.invoice.Invoice;
import com.teachmeskills.final_assignment.model.order.Order;
import com.teachmeskills.final_assignment.utils.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistic {

    private double totalCheckAmount;
    private double totalInvoiceAmount;
    private double totalOrderAmount;

    public void addCheck(Check check) {
        totalCheckAmount += check.getTotalAmount();
    }

    public void addInvoice(Invoice invoice) {
        totalInvoiceAmount += invoice.getTotalAmount();
    }

    public void addOrder(Order order) {
        totalOrderAmount += order.getTotalAmount();
    }

    public void printStatistics() {
        String statistic = String.format(Constants.HEAD_STATISTIC) +
                String.format("\nType\t\t\t\t\t|\tTotal amount\t|\tTotal count files\n") +
                String.format(Constants.DELIMITER_2) +
                String.format("\nTotal Check Amount:\t\t|\t%.2f\t\t|\t%d", totalCheckAmount, Check.countCheck) +
                String.format("\nTotal Invoice Amount:\t|\t%.2f\t\t\t|\t%d", totalInvoiceAmount, Invoice.countInvoice) +
                String.format("\nTotal Order Amount:\t\t|\t%.2f\t\t|\t%d\n", totalOrderAmount, Order.countOrder) +
                String.format(Constants.DELIMITER_1);
        System.out.println(statistic);
    }

    public void writeStatistic(String fileName) throws WrongStatisticException {
        LoggerService.logInfo("Saving to file");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
             writer.write(String.format(Constants.HEAD_STATISTIC) +
                    String.format("\nType\t\t\t\t\t|\tTotal amount\t|\tTotal count files\n") +
                    String.format(Constants.DELIMITER_2) +
                    String.format("\nTotal Check Amount:\t\t|\t%.2f\t\t|\t%d", totalCheckAmount, Check.countCheck) +
                    String.format("\nTotal Invoice Amount:\t|\t%.2f\t\t\t|\t%d", totalInvoiceAmount, Invoice.countInvoice) +
                    String.format("\nTotal Order Amount:\t\t|\t%.2f\t\t|\t%d\n", totalOrderAmount, Order.countOrder) +
                    String.format(Constants.DELIMITER_1));
            writer.newLine();
            LoggerService.logInfo("The file is was recorded successful");
        } catch (IOException e) {
            LoggerService.logError("Directory not found, file not was recorded");
            throw new WrongStatisticException("File statistic not was recorded\t" , Constants.ERROR_CODE_STATISTIC);
        }
    }

}
