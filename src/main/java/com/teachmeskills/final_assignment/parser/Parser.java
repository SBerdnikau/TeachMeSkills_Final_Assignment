package com.teachmeskills.final_assignment.parser;

import com.teachmeskills.final_assignment.exception.WrongParseException;
import com.teachmeskills.final_assignment.exception.WrongFileException;
import com.teachmeskills.final_assignment.exception.WrongStatisticException;
import com.teachmeskills.final_assignment.log.LoggerService;
import com.teachmeskills.final_assignment.model.check.Check;
import com.teachmeskills.final_assignment.model.invoice.Invoice;
import com.teachmeskills.final_assignment.model.order.Order;
import com.teachmeskills.final_assignment.properties.PropertiesManager;
import com.teachmeskills.final_assignment.session.SessionManager;
import com.teachmeskills.final_assignment.model.statistic.Statistic;
import com.teachmeskills.final_assignment.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Parser {

    public void processFile(String pathDirectory, SessionManager session) throws WrongFileException {

        if (session.isSessionValid()){

            LoggerService.logInfo(Constants.MESSAGE_VALIDATION_DIR);

            File rootDir = new File(pathDirectory);
            if (!rootDir.exists() || !rootDir.isDirectory()) {
                LoggerService.logError("Directory does not exist.");
                throw new WrongFileException("Directory does not exist.", Constants.ERROR_CODE_FILE);
            }

            Statistic statistics = new Statistic();

            LoggerService.logInfo(Constants.MESSAGE_PARSING);
            String documentExtension = PropertiesManager.loadProperties().getProperty("document.extension");
            String documentYear = PropertiesManager.loadProperties().getProperty("document.year");

            try (Stream<Path> filePathStream = Files.walk(rootDir.toPath())) {
                filePathStream
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().contains(documentYear) && path.getFileName().toString().endsWith(documentExtension))
                        .forEach(path -> {
                            try {
                                processFile(path.toString(), statistics);
                            } catch (WrongParseException e) {
                                LoggerService.logError("File not reading: " + e.getMessage() + " Error code: " + e.getCodeError());
                            }
                        });
            } catch (IOException e) {
                LoggerService.logError("The directory is not readable: " + e.getMessage());
                throw new WrongFileException(Constants.MESSAGE_FILE_NOT_FOUND, Constants.ERROR_CODE_FILE);
            }

            statistics.printStatistics();

            try {
                statistics.writeStatistic(Constants.PATH_TO_STATISTIC_FILE);
            } catch (WrongStatisticException e) {
                LoggerService.logError("Wrong writing statistics file\t" + e.getMessage() + "\tError code:\t" + e.getCodeError());
            }
        }else {
            LoggerService.logInfo(Constants.MESSAGE_SESSION_NOT_VALID);
        }
    }

    private static void processFile(String path, Statistic statistic) throws WrongParseException {
        try {
           List<String> lines = Files.readAllLines(Paths.get(path));
           Pattern checkPattern = Pattern.compile(Constants.CHECK_REGEX);
           Pattern invoicePattern = Pattern.compile(Constants.INVOICE_REGEX);
           Pattern orderPattern = Pattern.compile(Constants.ORDER_REGEX);

           for (String line : lines){

               if (line.contains("total")|| line.contains("Total")) {

                   LoggerService.logInfo("Checking check file:" + line);
                   Matcher checkMatcher = checkPattern.matcher(line);
                   if (checkMatcher.find()) {
                       statistic.addCheck(new Check(Double.parseDouble(checkMatcher.group(1).replace(",", "."))));
                       Check.countCheck++;
                       LoggerService.logInfo("Result checking is -> " + checkMatcher.hasMatch());
                   }

                   LoggerService.logInfo("Checking invoice file:" + line);
                   Matcher invoiceMatcher = invoicePattern.matcher(line);
                   if (invoiceMatcher.find()) {
                       statistic.addInvoice(new Invoice(Double.parseDouble(invoiceMatcher.group(1).replace(",", ""))));
                       Invoice.countInvoice++;
                       LoggerService.logInfo("Result checking is -> " + invoiceMatcher.hasMatch());
                   }

                   LoggerService.logInfo("Checking order file:" + line);
                   Matcher orderMatcher = orderPattern.matcher(line);
                   if (orderMatcher.find()) {
                       statistic.addOrder(new Order(Double.parseDouble(orderMatcher.group(1).replace(",", ""))));
                       Order.countOrder++;
                       LoggerService.logInfo("Result checking is -> " + orderMatcher.hasMatch());
                   }

               }else {
                   LoggerService.logInfo("Incorrect format pattern line: " + line);
               }

           }
        } catch (IOException e) {
            LoggerService.logError("The file is not readable " + e.getMessage());
            throw new WrongParseException("Error reading from file", Constants.ERROR_CODE_VALID);
        }

    }
}