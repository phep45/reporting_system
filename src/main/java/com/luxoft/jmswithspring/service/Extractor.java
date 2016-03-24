package com.luxoft.jmswithspring.service;

public class Extractor {

    private static final int USER_DATA_BEGIN = 10;
    private static final int USER_DATA_END = 35;
    private static final int TRANSACTION_ID_BEGIN = 0;
    private static final int TRANSACTION_ID_END = 10;
    private static final int TRANSACTION_DATA_BEGIN = 35;
    private static final int TRANSACTION_DATA_END = 52;
    private static final int SECURITY_DATA_BEGIN = 52;
    private static final int SECURITY_DATA_END = 96;

    public String extractUser(String input) {
        return input.substring(USER_DATA_BEGIN, USER_DATA_END);
    }

    public String extractTransaction(String input) {
        return input.substring(TRANSACTION_ID_BEGIN, TRANSACTION_ID_END) + input.substring(TRANSACTION_DATA_BEGIN, TRANSACTION_DATA_END);
    }

    public String extractSecurity(String input) {
        return input.substring(SECURITY_DATA_BEGIN, SECURITY_DATA_END);
    }


    public static void main(String[] args) {
        Extractor extractor = new Extractor();//39
        String str = "000000000500001     Stiven MeckalovMOVE_OUS0001122220000001010000000020.00000000202/13/201500123";
        System.out.println(str.length());
        System.out.println(extractor.extractUser(str));
        System.out.println(extractor.extractTransaction(str));
        System.out.println(extractor.extractSecurity(str));
    }

}
