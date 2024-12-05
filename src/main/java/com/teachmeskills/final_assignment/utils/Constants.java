package com.teachmeskills.final_assignment.utils;

public interface Constants {
    int ERROR_CODE_AUTH = 100;
    int ERROR_CODE_LOG = 200;
    int ERROR_CODE_FILE = 300;
    int ERROR_CODE_VALID = 400;
    int ERROR_CODE_STATISTIC = 500;

    String PATH_TO_STATISTIC_FILE = "src/main/resources/report/statistic.txt";
    String PATH_TO_LOG_INFO =  "src/main/resources/logs/info_log.txt";
    String PATH_TO_LOG_ERROR = "src/main/resources/logs/error_log.txt";
    String PATH_TO_PROPERTIES_FILE = "src/main/resources/properties/application.properties";
    String PATH_TO_REPORT_FILE =  "src/main/resources/report/statistic.txt";
    String PATH_TO_QRCODE =  "src/main/resources/qrcode/QRCode.png";

    String CHECK_REGEX = "Bill total amount EURO\\s*([0-9]*[.,][0-9]+)";
    String INVOICE_REGEX = "Total amount\\s*(\\d+)\\$";
    String ORDER_REGEX = "Order Total\\s*(\\d{1,3})(,\\d{3})*\\.(\\d{2})";

    String KEY_S3_NAME_FILE_REPORT = "statistic.txt";
    String KEY_2FA = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
    String EMAIL_2FA = "xxxx@gmail.com";
    String COMPANY_NAME_2FA = "TeachMeSkills C32";

    String MESSAGE_SUCCESS_UPLOAD_TO_AWS =  "File uploaded successfully. ETag: ";
    String MESSAGE_SESSION_NOT_VALID = "Session is not valid";
    String MESSAGE_FILE_NOT_FOUND =  "File not found ";
    String MESSAGE_FILE_PROPERTIES_NOT_FOUND = "File properties not found: ";
    String MESSAGE_FILE_NOT_WRITTEN = "File not written";
    String MESSAGE_DECRYPTING =  "Decrypting user input data...";
    String MESSAGE_RECEIVED_USER_DATA = "Received user data";
    String MESSAGE_CHECKING =  "Start checking login and password";
    String MESSAGE_AUTH_SUCCESSFUL = "User authorization successful";
    String MESSAGE_AUTH_FAILED =  "User authorization failed";
    String MESSAGE_INCORRECT_AUTH =  "Incorrect login or password entered ";
    String MESSAGE_PARSING = "Parsing  files...";
    String MESSAGE_VALIDATION_DIR = "Validation path to directory";
    String MESSAGE_CODE_ERROR = " Error code:  ";
    String MESSAGE_INVALID_CODE_2FA =  "Invalid 2FA Code. Try again...";
    String MESSAGE_ENTER_2FA = "Please enter 2fA code here: ";

    String DELIMITER_1 = "===================================================================";
    String DELIMITER_2 = "-------------------------------------------------------------------";
    String HEAD_STATISTIC = "====================FINANCIAL STATISTICS===========================";
}
