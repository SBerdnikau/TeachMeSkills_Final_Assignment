package com.teachmeskills.final_assignment.service;

import com.teachmeskills.final_assignment.exception.InvalidDirectoryException;
import com.teachmeskills.final_assignment.exception.InvalidFileException;
import com.teachmeskills.final_assignment.exception.InvalidWriteFileException;
import com.teachmeskills.final_assignment.log.Logger;
import com.teachmeskills.final_assignment.model.Document;
import com.teachmeskills.final_assignment.model.check.CheckImpl;
import com.teachmeskills.final_assignment.model.invoice.InvoiceImpl;
import com.teachmeskills.final_assignment.model.order.OrderImpl;
import com.teachmeskills.final_assignment.model.statistic.Statistic;
import com.teachmeskills.final_assignment.properties.PropertiesManager;
import com.teachmeskills.final_assignment.session.SessionManager;
import com.teachmeskills.final_assignment.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In this class, files are checked for validity, by extension and year,
 * the passed files are parsed, and we receive only the data for calculating the sum.
 * After processing, the data is written to the Statistics class for subsequent processing
 */
public class FileProcessor {
    private final Statistic statistic;
    private final Set<String> processedFiles;

    public FileProcessor(Statistic statistic) {
        this.statistic = statistic;
        this.processedFiles = new HashSet<>();
    }

    /**
     * In this method we read data from the directory and check it
     *
     * @param directoryPath path to directory
     * @param session       session object
     * @throws InvalidDirectoryException throw an exception if the folder is empty or not before the path
     * @throws InvalidFileException      throw an exception if the file is not valid
     */
    public void processDirectory(String directoryPath, SessionManager session) throws InvalidDirectoryException, InvalidFileException {

        if (session.isSessionValid()) {
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                throw new InvalidDirectoryException(Constants.MESSAGE_INVALID_DIRECTORY + directoryPath + Constants.ERROR_CODE_FILE + Constants.ERROR_CODE_FILE);
            }

            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                Logger.logInfo(Constants.MESSAGE_FILE_NOT_FOUND + directoryPath);
                throw new InvalidDirectoryException(Constants.MESSAGE_INVALID_DIRECTORY + directoryPath + Constants.MESSAGE_CODE_ERROR + Constants.ERROR_CODE_FILE);
            }

            for (File file : files) {
                if (!isValidFile(file)) {
                    try {
                        saveInvalidFile(file.getName());
                    } catch (InvalidWriteFileException e) {
                        Logger.logException(e);
                    }
                    Logger.logInfo("Skipping invalid file: " + file.getName());
                    continue;
                }

                try {
                    processFile(file);
                } catch (IOException e) {
                    Logger.logException(e);
                    System.out.println("Error processing file: " + file.getName() + Constants.MESSAGE_CODE_ERROR + Constants.ERROR_CODE_FILE);
                }
            }
        } else {
            Logger.logInfo(Constants.MESSAGE_SESSION_NOT_VALID);
            System.out.println(Constants.MESSAGE_SESSION_NOT_VALID);
        }
    }

    /**
     * In this method we parse the data of each file
     *
     * @param file name
     * @throws IOException throw a general file processing exception
     */
    private void processFile(File file) throws IOException {
        String fileName = file.getName().toLowerCase();
        String fileContent = Files.readString(file.toPath());

        if (processedFiles.contains(fileContent.hashCode() + "")) {
            Logger.logInfo("File already processed: " + fileName);
            return;
        }

        processedFiles.add(fileContent.hashCode() + "");

        if (fileName.contains("electric_bill")) {
            parseAmount(fileContent, Constants.CHECK_REGEX, new CheckImpl());
        } else if (fileName.contains("invoice")) {
            parseAmount(fileContent, Constants.INVOICE_REGEX, new InvoiceImpl());
        } else if (fileName.contains("order")) {
            parseAmount(fileContent, Constants.ORDER_REGEX, new OrderImpl());
        } else {
            Logger.logInfo("File does not match known formats: " + fileName);
        }
    }

    /**
     * This method checks files by year and extension received from properties
     *
     * @param file name
     * @return boolean
     * @throws InvalidFileException throw an exception if the file does not pass the check condition
     */
    private boolean isValidFile(File file) throws InvalidFileException {
        String documentExtension = PropertiesManager.loadProperties().getProperty("document.extension");
        String documentYear = PropertiesManager.loadProperties().getProperty("document.year");
        return file.isFile() && file.getName().endsWith(documentExtension) && file.getName().contains(documentYear);
    }

    /**
     * In this method, we check the file according to the pattern and if the condition is met,
     * we write the amount from the file to the statistics
     *
     * @param content  read file lines
     * @param regex    regular expression for file processing
     * @param document object to be processed
     */
    private void parseAmount(String content, String regex, Document document) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            if (document instanceof OrderImpl) {
                statistic.addOrder(new OrderImpl(Double.parseDouble(matcher.group(1).replace(",", ""))));
                Logger.logInfo("Result order file checking is  " + matcher.hasMatch());
            } else if (document instanceof InvoiceImpl) {
                statistic.addInvoice(new InvoiceImpl(Double.parseDouble(matcher.group(1).replace(",", "."))));
                Logger.logInfo("Result invoice file checking is  " + matcher.hasMatch());
            } else if (document instanceof CheckImpl) {
                statistic.addCheck(new CheckImpl(Double.parseDouble(matcher.group(1).replace(",", "."))));
                Logger.logInfo("Result check file checking is  " + matcher.hasMatch());
            }
        } else {
            Logger.logInfo(Constants.MESSAGE_INCORRECT_FORMAT);
        }
    }

    /**
     * In this method we save invalid files in a file
     *
     * @param message name file
     * @throws InvalidWriteFileException throw an exception if there is no file or directory
     */
    private static void saveInvalidFile(String message) throws InvalidWriteFileException {
        String infoMessage = message + "\n";
        try {
            Files.write(Paths.get(Constants.PATH_TO_INVALID_DOCUMENT), infoMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new InvalidWriteFileException(Constants.MESSAGE_FILE_NOT_WRITTEN + e.getMessage());
        }
    }
}