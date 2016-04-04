package com.luxoft.jmswithspring.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CountryCodeHandler extends XmlAdapter<Integer, CountryCode>  {

    @Override
    public CountryCode unmarshal(Integer v) throws Exception {
        return CountryCode.match(v);
    }

    @Override
    public Integer marshal(CountryCode v) throws Exception {
        return null;
    }
}
