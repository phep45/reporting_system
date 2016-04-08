package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
public class SecurityXmlConverter extends XmlConverter<SecuritiesForBranches> {

    @Override
    public SecuritiesForBranches unmarshal(String xml) {
        SecuritiesForBranches securitiesForBranches;

        try {
            jaxbContext = JAXBContext.newInstance(SecuritiesForBranches.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            securitiesForBranches = (SecuritiesForBranches) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));


        } catch (JAXBException e) {
            log.info("", e);
            return null;
        }

        return securitiesForBranches;
    }

}
