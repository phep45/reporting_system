package com.luxoft.jmswithspring.service.slis;

import org.springframework.stereotype.Component;

@Component
public class Extractor {

    private static final int USER_DATA_BEGIN = 10;
    private static final int USER_DATA_END = 35;
    private static final int TRANSACTION_ID_BEGIN = 0;
    private static final int TRANSACTION_ID_END = 10;
    private static final int TRANSACTION_DATA_BEGIN = 35;
    private static final int TRANSACTION_DATA_END = 52;
    private static final int SECURITY_DATA_BEGIN = 52;
//    private static final int SECURITY_DATA_END = 96;

    public String extractUser(String input) {
        return input.substring(USER_DATA_BEGIN, USER_DATA_END);
    }

    public String extractTransaction(String input) {
        return input.substring(TRANSACTION_ID_BEGIN, TRANSACTION_ID_END) + input.substring(TRANSACTION_DATA_BEGIN, TRANSACTION_DATA_END);
    }

    public String extractSecurities(String input) {
        return input.substring(SECURITY_DATA_BEGIN);
    }

}
