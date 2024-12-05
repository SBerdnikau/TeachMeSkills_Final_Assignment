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
                System.out.print(Constants.MESSAGE_ENTER_2FA);
                String code = scanner.nextLine();
                if (code.equals(TwoFactorAuthentication.getTOTPCode(secretKey))) {
                    System.out.println("Logged in successfully");
                    break;
                } else {
                    System.out.println(Constants.MESSAGE_INVALID_CODE_2FA);
                }
            }

            while (true) {
                System.out.print("Insert login: ");
                inputLogin = scanner.nextLine().trim();
                System.out.print("Insert password: ");
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
                            LoggerService.logError("File is not reading: " + e.getMessage() + Constants.MESSAGE_CODE_ERROR + e.getCodeError());
                        }
                    } else {
                        LoggerService.logInfo(Constants.MESSAGE_SESSION_NOT_VALID);
                    }
                }catch (WrongAuthException e){
                    LoggerService.logError("Authorization error: " + e.getMessage() + Constants.MESSAGE_CODE_ERROR + e.getErrorCode());
                }
        }catch (Exception e){
            LoggerService.logError("General reading error: " + e.getMessage());
        }
    }
}