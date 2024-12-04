package com.teachmeskills.final_assignment.utils;

public interface Constants {
    String PATH_TO_STATISTIC_FILE = "src/main/resources/report/statistic.txt";
    String CHECK_REGEX = "Bill total amount EURO\\s*([0-9]*[.,][0-9]+)";
    String INVOICE_REGEX = "Total amount\\s*(\\d+)\\$";
    String ORDER_REGEX = "Order Total\\s*(\\d{1,3})(,\\d{3})*\\.(\\d{2})";
    String PATH_TO_PROPERTIES_FILE = "src/main/resources/properties/application.properties";
    String LINE_DELIMITER = "=======================================================";
    int ERROR_CODE_AUTH = 100;
    int ERROR_CODE_LOG = 200;
    int ERROR_CODE_FILE = 300;
    int ERROR_CODE_VALID = 400;
    int ERROR_CODE_STATISTIC = 500;
    String PATH_TO_LOG_INFO =  "src/main/resources/logs/info_log.txt";
    String PATH_TO_LOG_ERROR = "src/main/resources/logs/error_log.txt";
    String MESSAGE_FILE_NOT_WRITTEN = "File not written";
    String MESSAGE_HEAD_STATISTIC = "====================STATISTIC===========================";
    String KEY_S3_NAME_FILE_REPORT = "statistic.txt";
    String PATH_TO_REPORT_FILE =  "src/main/resources/report/statistic.txt";
    String KEY_2FA = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
    String EMAIL_2FA = "xxxx@gmail.com";
    String COMPANY_NAME_2FA = "TeachMeSkills C32";
    String PATH_TO_QRCODE =  "src/main/resources/qrcode/QRCode.png";
    String STATUS_SUCCESS_UPLOAD_TO_AWS =  "File uploaded successfully. ETag: ";
}
