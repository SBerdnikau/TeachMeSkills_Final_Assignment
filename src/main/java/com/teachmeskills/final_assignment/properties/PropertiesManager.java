package com.teachmeskills.final_assignment.properties;

import com.teachmeskills.final_assignment.exception.InvalidFileException;
import com.teachmeskills.final_assignment.log.Logger;
import com.teachmeskills.final_assignment.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * class for reading data from property file
 */
public class PropertiesManager {

    public static Properties loadProperties() throws InvalidFileException {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(Constants.PATH_TO_PROPERTIES_FILE));
            properties.load(inputStream);
        } catch (IOException e) {
         //   Logger.logException(Constants.MESSAGE_FILE_PROPERTIES_NOT_FOUND + e.getMessage());
            throw new InvalidFileException(Constants.MESSAGE_FILE_PROPERTIES_NOT_FOUND, Constants.ERROR_CODE_FILE);
        }

        return properties;
    }
}