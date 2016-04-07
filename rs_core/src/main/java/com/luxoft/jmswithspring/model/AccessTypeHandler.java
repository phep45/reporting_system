package com.luxoft.jmswithspring.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AccessTypeHandler extends XmlAdapter<String, AccessType> {
    @Override
    public AccessType unmarshal(String v) throws Exception {
        return AccessType.valueOf(v.toUpperCase());
    }

    @Override
    public String marshal(AccessType v) throws Exception {
        throw new UnsupportedOperationException();
    }
}
