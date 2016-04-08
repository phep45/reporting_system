package com.luxoft.jmswithspring.model;

import com.luxoft.jmswithspring.model.exception.InvalidCountryCodeException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CountryCodeHandler extends XmlAdapter<Integer, CountryCode>  {

    @Override
    public CountryCode unmarshal(Integer v) throws InvalidCountryCodeException {
        CountryCode code = CountryCode.match(v);
        if(code != null) {
            return code;
        }
        else {
            throw new IllegalArgumentException("No code fot this value");
        }
    }

    @Override
    public Integer marshal(CountryCode v) throws Exception {
        throw new UnsupportedOperationException();
    }
}
