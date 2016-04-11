package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.AccessType;
import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import com.luxoft.jmswithspring.model.Security;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityXmlConverterTest {

    private static final File INPUT_FILE = new File("src/test/resources/security_branches.xml");
    private SecurityXmlConverter securityXmlConverter = new SecurityXmlConverter();

    private String input;

    @Before
    public void setUp() throws IOException {
        input = FileUtils.readFileToString(INPUT_FILE);
    }

    @Test
    public void shouldUnmarshalCorrectly() {
        SecuritiesForBranches expected = new SecuritiesForBranches();
        expected.setSecurities(createList());
        SecuritiesForBranches result =  securityXmlConverter.unmarshal(input);

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
