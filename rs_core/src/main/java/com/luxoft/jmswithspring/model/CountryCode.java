package com.luxoft.jmswithspring.model;

public enum CountryCode {

    US(100),
    GB(110),
    PL(120),
    RU(130),
    EU(140),
    JP(150),
    CH(160),
    UA(170),
    CK(180),
    AU(190);

    private Integer code;

    private CountryCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CountryCode match(Integer value) {
        for(CountryCode countryCode : values()) {
            if(value == countryCode.code)
                return countryCode;
        }
        return null;
    }
}


