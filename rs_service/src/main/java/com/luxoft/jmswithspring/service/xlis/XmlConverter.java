package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;

public abstract class XmlConverter<T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected JAXBContext jaxbContext;

    public abstract T unmarshal(String xml) throws CorruptedDataException;

}
