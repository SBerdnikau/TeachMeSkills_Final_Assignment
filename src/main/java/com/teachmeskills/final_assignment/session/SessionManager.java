package com.teachmeskills.final_assignment.session;

import com.teachmeskills.final_assignment.exception.WrongFileException;
import com.teachmeskills.final_assignment.log.LoggerService;
import com.teachmeskills.final_assignment.properties.PropertiesManager;
import com.teachmeskills.final_assignment.utils.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class generates a token for the user's session and
 * returns the session duration.
 */
public class SessionManager {

    private String accessToken;
    private Date expDate;

    public SessionManager() {
        setAccessToken();
        setExpDate();
    }

    /**
     *to check if the session is valid
     * @return session validity
     */
    public boolean isSessionValid(){
        return  this.accessToken.length() == 16 && this.expDate.after(new Date());
    }

    /**
     * to generate a token
     */
    private void setAccessToken() {
        String symbols = "abcdefghijklmnopqrstuvwxyz0123456789";

        this.accessToken =  new Random().ints(16, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    /**
     * to set the session life date
     */
    private void setExpDate()  {
        try {
            int sessionDuration = Integer.parseInt(PropertiesManager.loadProperties().getProperty("session.duration_in_minute","5"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, sessionDuration);
            this.expDate = calendar.getTime();
        } catch (WrongFileException e) {
            LoggerService.logError("Error reading property file: " + e.getMessage() + Constants.MESSAGE_CODE_ERROR + e.getCodeError());
        }
    }
}
