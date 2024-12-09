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


public class FileProcessor {
    private final Statistic statistic;
    private final Set<String> processedFiles;

    public FileProcessor(Statistic statistic) {
        this.statistic = statistic;
        this.processedFiles = new HashSet<>();
    }

    public void processDirectory(String directoryPath, SessionManager session) throws InvalidDirectoryException, InvalidFileException {

        if(session.isSessionValid()){
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                throw new InvalidDirectoryException("Invalid directory path: " + directoryPath, Constants.ERROR_CODE_FILE);
            }

            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                Logger.logInfo("No files found in directory: " + directoryPath);
                throw new InvalidDirectoryException("Invalid directory path: " + directoryPath, Constants.ERROR_CODE_FILE);
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
                    System.out.println("Error processing file: " + file.getName() + ". Message: " + e.getMessage());
                }
            }
        }else {
            Logger.logInfo(Constants.MESSAGE_SESSION_NOT_VALID);
            System.out.println(Constants.MESSAGE_SESSION_NOT_VALID);
        }
    }

    private void processFile(File file) throws  IOException {
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

    private boolean isValidFile(File file) throws InvalidFileException {
       String documentExtension = PropertiesManager.loadProperties().getProperty("document.extension");
       String documentYear = PropertiesManager.loadProperties().getProperty("document.year");
       return file.isFile() && file.getName().endsWith(documentExtension) && file.getName().contains(documentYear);
    }

    private void parseAmount(String content, String regex, Document document) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())  {
            if (document instanceof OrderImpl){
                statistic.addOrder(new OrderImpl(Double.parseDouble(matcher.group(1).replace(",", ""))));
                Logger.logInfo("Result order file checking is  " + matcher.hasMatch() );
            } else if (document instanceof InvoiceImpl) {
                statistic.addInvoice(new InvoiceImpl(Double.parseDouble(matcher.group(1).replace(",", "."))));
                Logger.logInfo("Result invoice file checking is  " + matcher.hasMatch() );
            }else if (document instanceof CheckImpl){
                statistic.addCheck(new CheckImpl(Double.parseDouble(matcher.group(1).replace(",", "."))));
                Logger.logInfo("Result check file checking is  " + matcher.hasMatch() );
            }
        }else {
            Logger.logInfo("Incorrect format file");
        }
    }

    private static void saveInvalidFile(String message) throws  InvalidWriteFileException {
       String infoMessage =  message + "\n";
       try {
           Files.write(Paths.get("src/main/resources/invalid/invalid_documents.txt"), infoMessage.getBytes(), StandardOpenOption.APPEND) ;
       }catch (IOException e){
           throw new InvalidWriteFileException(Constants.MESSAGE_FILE_NOT_WRITTEN + e.getMessage());
       }
    }
}