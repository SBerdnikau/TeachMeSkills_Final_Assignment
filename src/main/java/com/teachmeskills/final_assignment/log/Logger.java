package com.teachmeskills.final_assignment.log;

import com.teachmeskills.final_assignment.exception.InvalidWriteLoggerException;
import com.teachmeskills.final_assignment.utils.Constants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public Logger() {
    }

    public static void logInfo(String message)  {
        try {
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String dateTime = formater.format(new Date());
            String infoMessage = "[INFO]\t\t" + dateTime + "\t\t" + message + "\n";
            Files.write(Paths.get(Constants.PATH_TO_LOG_EVENT), infoMessage.getBytes(), StandardOpenOption.APPEND);
        }catch (Exception e){
            throw new InvalidWriteLoggerException(Constants.MESSAGE_FILE_NOT_WRITTEN, Constants.ERROR_CODE_LOG);
        }
    }

    public static void logException(Exception exception) throws InvalidWriteLoggerException {
        try {
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String dateTime = formater.format(new Date());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[ERROR]\t\t" + dateTime + "\t\t" + exception.getMessage() + "\n");
            stringBuilder.append(exception.getStackTrace());

            StackTraceElement[] stackTrace = exception.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                stringBuilder.append("\t" + element.toString() + "\n");
            }

            Files.write(Paths.get(Constants.PATH_TO_LOG_ERROR), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND) ;
        }catch (Exception e){
            throw new InvalidWriteLoggerException(Constants.MESSAGE_FILE_NOT_WRITTEN, Constants.ERROR_CODE_LOG);
        }
    }
}
