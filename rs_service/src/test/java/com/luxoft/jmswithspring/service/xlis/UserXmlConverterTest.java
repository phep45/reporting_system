package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.User;
import org.junit.Assert;
import org.junit.Test;

public class UserXmlConverterTest {

    private final String input = "<tran> <tran_id>120</tran_id> <user> <user_id>12</user_id> <f_name>Oleksii</f_name> <s_name>Fri</s_name> <br_date>23-05-2001</br_date> </user> <type>BUY</type> <country_code>124</country_code> <branch> <id>123</id> <address>Ckotland str. test 54</address> </branch> <lots> <date>23-12-1999</date> <lot_id>123</lot_id> <price>500.21</price> <amount>5</amount> <sec_id>655</sec_id> </lots> </tran>";

    private XmlConverter<User> userXmlConverter = new UserXmlConverter();

    @Test
    public void shouldReturnUser() throws CorruptedDataException {

        User userFromXml = userXmlConverter.unmarshal(input);
        User expectedUser = User.builder()
                .withUserId(12)
                .withFirstName("Oleksii")
                .withSurname("Fri")
                .withBirthDate("23-05-2001")
                .build();

        Assert.assertEquals(expectedUser, userFromXml);
    }

}
