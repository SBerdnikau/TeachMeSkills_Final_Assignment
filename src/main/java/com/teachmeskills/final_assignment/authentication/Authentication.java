package com.teachmeskills.final_assignment.authentication;

import com.teachmeskills.final_assignment.exception.InvalidAuthException;
import com.teachmeskills.final_assignment.log.Logger;
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
     * @throws InvalidAuthException - thrown when authorization fails
     */
    public SessionManager auth(String login, String password) throws InvalidAuthException{

        MockStorage storageLikeDB = new MockStorage();
        Logger.logInfo(Constants.MESSAGE_RECEIVED_USER_DATA);

        Logger.logInfo("Decrypting login: " + login);
        String loginFromDB = EncryptService.decrypt(storageLikeDB.getLogin());
        Logger.logInfo("Decrypted password: " + password);
        String passwordFromDB = EncryptService.decrypt(storageLikeDB.getPassword());

        Logger.logInfo(Constants.MESSAGE_CHECKING_LOGIN);
        boolean isLogin = login.toLowerCase().equals(loginFromDB);
        Logger.logInfo("Finish checking login is " + isLogin);

        Logger.logInfo(Constants.MESSAGE_CHECKING_PASSWORD);
        boolean isPassword =  password.equals(passwordFromDB);
        Logger.logInfo("Finish checking password is " + isPassword);

        if(isLogin && isPassword) {
            Logger.logInfo(Constants.MESSAGE_AUTH_SUCCESSFUL);
            Logger.logInfo(Constants.DELIMITER_2);
            return new SessionManager();
        }else {
            Logger.logInfo(Constants.MESSAGE_AUTH_FAILED);
            Logger.logInfo(Constants.DELIMITER_1);
            throw new InvalidAuthException(Constants.MESSAGE_INCORRECT_AUTH, Constants.ERROR_CODE_AUTH);
        }
    }
}