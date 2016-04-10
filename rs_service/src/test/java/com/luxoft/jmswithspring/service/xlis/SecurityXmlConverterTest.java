package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.AccessType;
import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import com.luxoft.jmswithspring.model.Security;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SecurityXmlConverterTest {

    private SecurityXmlConverter securityXmlConverter = new SecurityXmlConverter();

    private static final String INPUT =
            "<securities_branches>\n" +
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

    @Test
    public void shouldUnmarshalCorrectly() {
        SecuritiesForBranches expected = new SecuritiesForBranches();
        expected.setSecurities(createList());
        SecuritiesForBranches result =  securityXmlConverter.unmarshal(INPUT);

        Assert.assertEquals(expected, result);
    }

    private List<Security> createList() {
        List<Security> securities = new ArrayList<>();

        securities.add(Security.builder().withId(124).withSecurityId(654).withSecurityId(655).withSecurityId(656).withAccessType(AccessType.ALLOW).withDate("21-11-2001").build());
        securities.add(Security.builder().withId(124).withSecurityId(123).withSecurityId(321).withSecurityId(321).withAccessType(AccessType.FORBID).withDate("21-11-2001").build());
        securities.add(Security.builder().withId(122).withSecurityId(6542).withSecurityId(6553).withSecurityId(6562).withAccessType(AccessType.ALLOW).withDate("21-11-2001").build());

        return securities;
    }


}
