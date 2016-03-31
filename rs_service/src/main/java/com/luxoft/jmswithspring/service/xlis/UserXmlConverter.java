package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserXmlConverter extends XmlConverter<User> {

    public static final String USER_REGEX = "<user>.*</user>";

    @Override
    public User unmarshal(String xml) {
        User user = null;

        Pattern pattern = Pattern.compile(USER_REGEX);
        Matcher matcher = pattern.matcher(xml);

        if(matcher.find()) {
            String str = matcher.group();
            try {
                jaxbContext = JAXBContext.newInstance(User.class);

                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                user = (User) unmarshaller.unmarshal(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));

            } catch (JAXBException e) {
                log.info("",e);
                return null;
            }
        }

        return user;
    }

}
