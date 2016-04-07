package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.SecuritiesForBranches;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

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

    public static void main(String[] args) {
        SecurityXmlConverter securityXmlConverter = new SecurityXmlConverter();
        String xml = "<securities_branches>\n" +
                "    <security branch_id=\"124\" date=\"21-11-2001\" type=\"allow\">\n" +
                "        <sec_id>654</sec_id>\n" +
                "        <sec_id>655</sec_id>\n" +
                "        <sec_id>656</sec_id>\n" +
                "    </security>\n" +
                "    <security branch_id=\"124\" date=\"21-11-2001\" type=\"forbid\">\n" +
                "        <sec_id>123</sec_id>\n" +
                "        <sec_id>321</sec_id>\n" +
                "        <sec_id>321</sec_id>\n" +
                "    </security>\n" +
                "    <security branch_id=\"122\" date=\"21-11-2001\" type=\"allow\">\n" +
                "        <sec_id>6542</sec_id>\n" +
                "        <sec_id>6553</sec_id>\n" +
                "        <sec_id>6562</sec_id>\n" +
                "    </security>\n" +
                "</securities_branches>";


        SecuritiesForBranches sec = securityXmlConverter.unmarshal(xml);
        System.out.println(sec);
    }
}
