package com.luxoft.reportingsystem.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Queue;

public class XLISCollector {
    private static final Logger log = LoggerFactory.getLogger(XLISCollector.class);

    public GenericData collect(Queue<Message> queue) throws JMSException {
        String xml = ((TextMessage) queue.poll()).getText();
        log.trace("XML polled from queue: {}", xml);
        return process(xml);
    }

    private GenericData process(String xml) {
        GenericData data = null;
        StringReader reader = new StringReader(xml);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GenericData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            log.trace("Processing XML");
            data = (GenericData) jaxbUnmarshaller.unmarshal(reader);

        } catch (JAXBException e) {
            log.error("Could not unmarshall.", e);
        }

        return data;
    }
}
