package com.teachmeskills.final_assignment;

import com.teachmeskills.final_assignment.authentication.Authentication;
import com.teachmeskills.final_assignment.authentication.TwoFactorAuthentication;
import com.teachmeskills.final_assignment.aws.S3Uploader;
import com.teachmeskills.final_assignment.exception.InvalidAuthException;
import com.teachmeskills.final_assignment.exception.InvalidLoginException;
import com.teachmeskills.final_assignment.exception.InvalidPasswordException;
import com.teachmeskills.final_assignment.log.Logger;
import com.teachmeskills.final_assignment.model.statistic.Statistic;
import com.teachmeskills.final_assignment.service.FileProcessor;
import com.teachmeskills.final_assignment.session.SessionManager;
import com.teachmeskills.final_assignment.utils.AuthValidator;
import com.teachmeskills.final_assignment.utils.Constants;

import java.util.Scanner;

public class ApplicationRunner {
    public static void main(String[] args) {
        String directoryPath;
        String login;
        String password;
        String barCodeUrl = TwoFactorAuthentication.getGoogleAuthenticatorBarCode(Constants.KEY_2FA, Constants.EMAIL_2FA, Constants.COMPANY_NAME_2FA);

        Logger.logInfo("Start program");
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter login: ");
            login = scanner.nextLine().trim();
            try {
                if (AuthValidator.isValidLogin(login)) {
                    Logger.logInfo("Login is valid");
                }
            } catch (InvalidLoginException e) {
                Logger.logException(e);
            }

            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();

            try {
                if (AuthValidator.isValidPassword(password)) {
                    Logger.logInfo("Password is valid");
                }
            } catch (InvalidPasswordException e) {
                Logger.logException(e);
            }

            TwoFactorAuthentication.createQRCode(barCodeUrl, Constants.PATH_TO_QRCODE, 400, 400);
            Logger.logInfo("QR code created");
            Logger.logInfo("Waiting for two factor authentication...");

            while (true) {
                System.out.print(Constants.MESSAGE_ENTER_2FA);
                String code = scanner.nextLine().trim();
                if (code.equals(TwoFactorAuthentication.getTOTPCode(Constants.KEY_2FA))) {
                    Logger.logInfo("Logged in successfully\n" + Constants.DELIMITER_2);
                    System.out.println("Logged in successfully");
                    break;
                } else {
                    Logger.logInfo("Invalid 2FA Code. Try again...\n" + Constants.DELIMITER_2);
                    System.out.println(Constants.MESSAGE_INVALID_CODE_2FA);
                }
            }

            try {
                Authentication authService = new Authentication();
                SessionManager session = authService.auth(login, password);

                Logger.logInfo("Session created");
                if (session.isSessionValid()) {

                    while (true) {
                        Logger.logInfo("Enter path to directory");
                        System.out.print("Enter path to directory: ");
                        directoryPath = scanner.nextLine().trim();
                        if (directoryPath.isEmpty()) {
                            Logger.logInfo("Path cannot be empty. Please, enter path again..");
                            System.out.println("Path cannot be empty. Please, enter path again..");
                        } else {
                            break;
                        }
                    }

                    Statistic statistic = new Statistic();
                    FileProcessor fileProcessor = new FileProcessor(statistic);

                    Logger.logInfo("Processing check files...");
                    fileProcessor.processDirectory(directoryPath + "/checks", session);

                    Logger.logInfo("Processing invoices files...");
                    fileProcessor.processDirectory(directoryPath + "/invoices", session);

                    Logger.logInfo("Processing orders files...");
                    fileProcessor.processDirectory(directoryPath + "/orders", session);

                    Logger.logInfo("Show statistic to the console");
                    statistic.printStatistics();

                    Logger.logInfo("Save to file report");
                    statistic.writeStatistic();

                    Logger.logInfo("Upload file report to AWS server start");
                    S3Uploader.s3();

                    Logger.logInfo("Finish program");

                } else {
                    Logger.logInfo(Constants.MESSAGE_SESSION_NOT_VALID);
                }
            } catch (InvalidAuthException e) {
                Logger.logException(e);
                System.out.println("Authorization exception: " + e.getMessage() + Constants.MESSAGE_CODE_ERROR + e.getErrorCode());
            }
        } catch (Exception e) {
            Logger.logException(e);
            System.out.println("General exception: " + e.getMessage());
        }
    }
}