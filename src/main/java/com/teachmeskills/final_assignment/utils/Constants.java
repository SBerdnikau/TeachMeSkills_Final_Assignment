package com.teachmeskills.final_assignment.utils;

public interface Constants {
    int ERROR_CODE_AUTH = 100;
    int ERROR_CODE_LOG = 200;
    int ERROR_CODE_FILE = 300;
    int ERROR_CODE_VALID = 400;
    int ERROR_CODE_STATISTIC = 500;
    int ERROR_CODE_UPLOAD   = 600;

    String PATH_TO_STATISTIC_FILE = "src/main/resources/report/statistic.txt";
    String PATH_TO_LOG_EVENT =  "src/main/resources/log/event_log/event_log.txt";
    String PATH_TO_LOG_ERROR = "src/main/resources/log/error_log/error_log.txt";
    String PATH_TO_PROPERTIES_FILE = "src/main/resources/properties/application.properties";
    String PATH_TO_REPORT_FILE =  "src/main/resources/report/statistic.txt";
    String PATH_TO_QRCODE =  "src/main/resources/qrcode/QRCode.png";
    String PATH_TO_INVALID_DOCUMENT =  "src/main/resources/invalid/invalid_documents.txt";

    String CHECK_REGEX = "Bill total amount EURO\\s*([0-9]*[.,][0-9]+)";
    String INVOICE_REGEX = "(?:Total Amount|Total amount)\\s*[\\$]?(\\d{1,3}(?:,\\d{3})*(?:\\.\\d{2})?|\\d+(?:\\.\\d{2})?)";
    String ORDER_REGEX = "Order Total\\s+([\\d\\s,]+\\.\\d{2})";

    String KEY_S3_NAME_FILE_REPORT = "statistic.txt";
    String KEY_2FA = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
    String EMAIL_2FA = "xxxx@gmail.com";
    String COMPANY_NAME_2FA = "TeachMeSkills C32";

    String MESSAGE_SUCCESS_UPLOAD_TO_AWS =  "File uploaded successfully. ETag: ";
    String MESSAGE_SESSION_NOT_VALID = "Session is not valid";
    String MESSAGE_FILE_NOT_FOUND =  "No files found in directory: ";
    String MESSAGE_FILE_PROPERTIES_NOT_FOUND = "File properties not found: ";
    String MESSAGE_FILE_NOT_WRITTEN = "File not written";
    String MESSAGE_DECRYPTING =  "Decrypting user input data";
    String MESSAGE_RECEIVED_USER_DATA = "Received user data";
    String MESSAGE_CHECKING_LOGIN =  "Start checking login";
    String MESSAGE_CHECKING_PASSWORD =  "Start checking password";
    String MESSAGE_AUTH_SUCCESSFUL = "User authorization successful";
    String MESSAGE_AUTH_FAILED =  "User authorization failed";
    String MESSAGE_INCORRECT_AUTH =  "Incorrect login or password entered ";
    String MESSAGE_CODE_ERROR = " Error code:  ";
    String MESSAGE_INVALID_CODE_2FA =  "Invalid 2FA Code.";
    String MESSAGE_ENTER_2FA = "Enter 2fA code here: ";
    String MESSAGE_INVALID_DIRECTORY = "Invalid directory path: ";
    String MESSAGE_EXCEPTION_UPLOAD_AWS = "General exception connection or uploaded ";
    String MESSAGE_EXCEPTION_READ_PROPERTY = "Error reading property file: ";
    String MESSAGE_PASSWORD_IS_EMPTY = "Password must not be empty.";
    String MESSAGE_LOGIN_IS_EMPTY = "Login must not be empty.";
    String MESSAGE_LOGIN_TOO_SHORT = "Password is too short.";
    String MESSAGE_INCORRECT_FORMAT = "Incorrect format file";


    String DELIMITER_1 = "===================================================================";
    String DELIMITER_2 = "-------------------------------------------------------------------";
    String HEAD_STATISTIC = "====================FINANCIAL STATISTICS===========================";
}
