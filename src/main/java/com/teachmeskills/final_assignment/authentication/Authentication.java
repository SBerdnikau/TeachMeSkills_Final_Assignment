package com.teachmeskills.final_assignment.authentication;

import com.teachmeskills.final_assignment.exception.WrongAuthException;
import com.teachmeskills.final_assignment.log.LoggerService;
import com.teachmeskills.final_assignment.storage.MockStorage;
import com.teachmeskills.final_assignment.service.EncryptService;
import com.teachmeskills.final_assignment.session.SessionManager;
import com.teachmeskills.final_assignment.utils.Constants;

/**
 * class for handling user authorization
 */
public class Authentication {

    public SessionManager auth(String login, String password) throws WrongAuthException {

        MockStorage storageLikeDB = new MockStorage();
        LoggerService.logInfo("Received user data");

        String loginFromDB = EncryptService.decrypt(storageLikeDB.getLogin());
        String passwordFromDB = EncryptService.decrypt(storageLikeDB.getPassword());

        LoggerService.logInfo("Start checking login and password");

        boolean result = login.toLowerCase().equals(loginFromDB) && password.equals(passwordFromDB);

        if(login.toLowerCase().equals(loginFromDB)  && password.equals(passwordFromDB)) {
            LoggerService.logInfo("End of login and password verification:" + result);
            LoggerService.logInfo("User authorization successful");
            System.out.println(Constants.LINE_DELIMITER);
            return new SessionManager();
        }else {
            LoggerService.logInfo("End of login and password verification: " + result);
            LoggerService.logError("User authorization failed");
            System.out.println(Constants.LINE_DELIMITER);
            throw new WrongAuthException("Incorrect login or password entered ", Constants.ERROR_CODE_AUTH);
        }
    }
}
