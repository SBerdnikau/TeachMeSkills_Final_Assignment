package com.teachmeskills.final_assignment.log;

import com.teachmeskills.final_assignment.exception.WrongLoggerException;
import com.teachmeskills.final_assignment.utils.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerService {

    private final static SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public LoggerService() {
    }

    public static void logInfo(String message)  {
        System.out.println("Info-logger: " + message);
        try(BufferedWriter infoLogger = new BufferedWriter(new FileWriter(Constants.PATH_TO_LOG_INFO, true))) {
            infoLogger.write("Info-logger: " + message + "\t" + formater.format(new Date()));
            infoLogger.newLine();
        }catch (IOException e){
            throw new WrongLoggerException(Constants.MESSAGE_FILE_NOT_WRITTEN, Constants.ERROR_CODE_LOG);
        }
    }

    public static void logError(String message) throws WrongLoggerException {
        System.out.println("Error-logger: " + message);
        try(BufferedWriter errorLogger = new BufferedWriter(new FileWriter(Constants.PATH_TO_LOG_ERROR, true))) {
            errorLogger.write("Error logger: " + message + "\t" + formater.format(new Date()));
            errorLogger.newLine();
        } catch (IOException e) {
            throw new WrongLoggerException(Constants.MESSAGE_FILE_NOT_WRITTEN , Constants.ERROR_CODE_LOG);
        }
    }
}
