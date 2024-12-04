package com.teachmeskills.final_assignment;

import com.teachmeskills.final_assignment.authentication.TwoFactorAuthentication;
import com.teachmeskills.final_assignment.aws.S3Uploader;
import com.teachmeskills.final_assignment.exception.WrongAuthException;
import com.teachmeskills.final_assignment.exception.WrongFileException;
import com.teachmeskills.final_assignment.log.LoggerService;
import com.teachmeskills.final_assignment.authentication.Authentication;
import com.teachmeskills.final_assignment.utils.Constants;
import com.teachmeskills.final_assignment.validator.Validator;
import com.teachmeskills.final_assignment.session.SessionManager;

import java.util.Scanner;

public class ApplicationRunner {
    public static void main(String[] args)  {
        // src/main/resources/data
        String directoryPath;
        String inputLogin;
        String inputPass;
        String secretKey = Constants.KEY_2FA;
        String email = Constants.EMAIL_2FA;
        String companyName = Constants.COMPANY_NAME_2FA;
        String barCodeUrl = TwoFactorAuthentication.getGoogleAuthenticatorBarCode(secretKey, email, companyName);

        try(Scanner scanner = new Scanner(System.in)) {
            TwoFactorAuthentication.createQRCode(barCodeUrl, Constants.PATH_TO_QRCODE, 400, 400);

            while (true){
                System.out.print("Please enter 2fA code here: ");
                String code = scanner.nextLine();
                if (code.equals(TwoFactorAuthentication.getTOTPCode(secretKey))) {
                    System.out.println("Logged in successfully");
                    break;
                } else {
                    System.out.println("Invalid 2FA Code. Try again...");
                }
            }

            while (true) {
                System.out.print("Insert login: ");//admin
                inputLogin = scanner.nextLine().trim();
                System.out.print("Insert password: ");//TMC32Java
                inputPass = scanner.nextLine().trim();
                if (inputLogin.isEmpty() || inputPass.isEmpty()) {
                    LoggerService.logError("Login or password must not be empty. Try again...");
                } else {
                    break;
                }
            }

                Authentication authService = new Authentication();

                try{
                    SessionManager sessionClient1 = authService.auth(inputLogin, inputPass);

                    if (sessionClient1.isSessionValid()) {

                        while (true) {
                            System.out.print("Insert path to directory: ");
                            directoryPath = scanner.nextLine().trim();
                            if (directoryPath.isEmpty()) {
                                System.out.println("Path cannot be empty. Please, enter path again..");
                            } else {
                                break;
                            }
                        }

                        Validator parser = new Validator();

                        try {
                            parser.validationFile(directoryPath, sessionClient1);
                            S3Uploader.s3();//AWS Uploader service
                        } catch (WrongFileException e) {
                            LoggerService.logError("File is not reading: " + e.getMessage() + " Error code: " + e.getCodeError());
                        }
                    } else {
                        LoggerService.logInfo("Session is not valid");
                    }
                }catch (WrongAuthException e){
                    LoggerService.logError("Authorization error: " + e.getMessage() + " Error code: " + e.getErrorCode());
                }
        }catch (Exception e){
            LoggerService.logError("General reading error: " + e.getMessage());
        }
    }
}