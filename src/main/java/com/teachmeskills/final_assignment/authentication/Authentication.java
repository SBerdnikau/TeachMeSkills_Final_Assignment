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

    /**
     * @param login - insert user login
     * @param password - insert user password
     * @return if true, object SessionManager or return exception
     * @throws WrongAuthException - thrown when authorization fails
     */
    public SessionManager auth(String login, String password) throws WrongAuthException {

        MockStorage storageLikeDB = new MockStorage();
        LoggerService.logInfo(Constants.MESSAGE_RECEIVED_USER_DATA);

        String loginFromDB = EncryptService.decrypt(storageLikeDB.getLogin());
        String passwordFromDB = EncryptService.decrypt(storageLikeDB.getPassword());

        LoggerService.logInfo(Constants.MESSAGE_CHECKING);
        boolean result = login.toLowerCase().equals(loginFromDB) && password.equals(passwordFromDB);

        if(result) {
            LoggerService.logInfo(Constants.MESSAGE_AUTH_SUCCESSFUL);
            System.out.println(Constants.DELIMITER);
            return new SessionManager();
        }else {
            LoggerService.logError(Constants.MESSAGE_AUTH_FAILED);
            System.out.println(Constants.DELIMITER);
            throw new WrongAuthException(Constants.MESSAGE_INCORRECT_AUTH, Constants.ERROR_CODE_AUTH);
        }
    }
}