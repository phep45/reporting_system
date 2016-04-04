package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.User;
import org.apache.commons.lang3.math.NumberUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityXmlConverter extends XmlConverter<Security> {

    private static final String SEC_REGEX = "";

    @Override
    public Security unmarshal(String xml) {
        Security security = null;

        Pattern pattern = Pattern.compile(SEC_REGEX);
        Matcher matcher = pattern.matcher(xml);

        if(matcher.find()) {
            String str = matcher.group();
            try {
                jaxbContext = JAXBContext.newInstance(User.class);

                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                security = (Security) unmarshaller.unmarshal(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));


            } catch (JAXBException e) {
                log.info("",e);
                return null;
            }
        }

        return security;
    }
}
