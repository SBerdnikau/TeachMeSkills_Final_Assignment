package com.teachmeskills.final_assignment;

import com.teachmeskills.final_assignment.authentication.TwoFactorAuthentication;
import com.teachmeskills.final_assignment.exception.InvalidAuthException;
import com.teachmeskills.final_assignment.exception.InvalidFileException;
import com.teachmeskills.final_assignment.log.Logger;
import com.teachmeskills.final_assignment.authentication.Authentication;
import com.teachmeskills.final_assignment.model.statistic.Statistic;
import com.teachmeskills.final_assignment.utils.AuthValidator;
import com.teachmeskills.final_assignment.utils.Constants;
import com.teachmeskills.final_assignment.service.FileProcessor;
import com.teachmeskills.final_assignment.session.SessionManager;

import java.util.Scanner;

public class ApplicationRunner {
    public static void main(String[] args) throws InvalidFileException {
        String directoryPath;
        String login;
        String password;
        String secretKey = Constants.KEY_2FA;
        String email = Constants.EMAIL_2FA;
        String companyName = Constants.COMPANY_NAME_2FA;
        String barCodeUrl = TwoFactorAuthentication.getGoogleAuthenticatorBarCode(secretKey, email, companyName);

        Logger.logInfo("Start program");
        try(Scanner scanner = new Scanner(System.in)) {
            TwoFactorAuthentication.createQRCode(barCodeUrl, Constants.PATH_TO_QRCODE, 400, 400);
            Logger.logInfo("QR code created");
            Logger.logInfo("Waiting for two factor authentication...");

            while (true){
                System.out.print(Constants.MESSAGE_ENTER_2FA);
                String code = scanner.nextLine().trim();

                if (code.equals(TwoFactorAuthentication.getTOTPCode(secretKey))) {
                    Logger.logInfo("Logged in successfully");
                    Logger.logInfo(Constants.DELIMITER_2);
                    System.out.println("Logged in successfully");
                    break;
                } else {
                    Logger.logInfo("Invalid 2FA Code. Try again...");
                    Logger.logInfo(Constants.DELIMITER_2);
                    System.out.println(Constants.MESSAGE_INVALID_CODE_2FA);
                }
            }

            while(true){
                System.out.print("Enter login: ");
                login = scanner.nextLine().trim();
                if (AuthValidator.isValidLogin(login)){
                    Logger.logInfo("Login is valid");
                    System.out.print("Enter password: ");
                    password = scanner.nextLine().trim();
                    if (AuthValidator.isValidPassword(password)){
                        Logger.logInfo("Password is valid");
                        break;
                    }
                }
            }

            try{
                Authentication authService = new Authentication();
                SessionManager session = authService.auth(login, password);
                Logger.logInfo("Session created " + session );
                if (session.isSessionValid()) {
                    while (true) {
                        System.out.print("Enter path to directory: ");
                        Logger.logInfo("Enter path to directory");
                        directoryPath = scanner.nextLine().trim();
                        if (directoryPath.isEmpty()) {
                            Logger.logInfo("Path cannot be empty. Please, enter path again..");
                            System.out.println("Path cannot be empty. Please, enter path again..");
                        } else {
                            break;
                        }
                    }

                    FileProcessor fileProcessor = new FileProcessor(new Statistic());
                    Logger.logInfo("Processing check files...");
                    fileProcessor.processDirectory(directoryPath + "/checks" , session);
                    Logger.logInfo("Processing invoices files...");
                    fileProcessor.processDirectory(directoryPath + "/invoices" , session );
                    Logger.logInfo("Processing orders files...");
                    fileProcessor.processDirectory(directoryPath + "/orders" , session);
                    Logger.logInfo("Show statistic to the console");
                    fileProcessor.getStatistic().printStatistics();
                    Logger.logInfo("Save to file report");
                    fileProcessor.getStatistic().writeStatistic();
                    Logger.logInfo("Upload file report to AWS server");
                    //S3Uploader.s3();//AWS Uploader service
                    Logger.logInfo("Finish program");

                } else {
                    Logger.logInfo(Constants.MESSAGE_SESSION_NOT_VALID);
                }

            }catch (InvalidAuthException e){
                Logger.logException(e);
                System.out.println("Authorization exception: " + e.getMessage() + Constants.MESSAGE_CODE_ERROR + e.getErrorCode());
            }

        }catch (Exception e){
            Logger.logException(e);
            System.out.println("General exception: " + e.getMessage());
        }
    }
}