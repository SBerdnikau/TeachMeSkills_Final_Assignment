package com.teachmeskills.final_assignment.properties;

import com.teachmeskills.final_assignment.exception.WrongFileException;
import com.teachmeskills.final_assignment.log.LoggerService;
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

    public static Properties loadProperties() throws WrongFileException {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(Constants.PATH_TO_PROPERTIES_FILE));
            properties.load(inputStream);
        } catch (IOException e) {
            LoggerService.logError("File properties not found: "  + e.getMessage());
            throw new WrongFileException("File properties not found", Constants.ERROR_CODE_FILE);
        }

        return properties;
    }
}
