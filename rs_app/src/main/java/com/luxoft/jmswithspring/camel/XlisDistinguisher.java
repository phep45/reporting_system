package com.luxoft.jmswithspring.camel;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Qualifier("xlisDistinguisher")
public class XlisDistinguisher {
    private static final String SEC_FOR_BRANCHES_REGEX = "<securities_branches>.*</securities_branches>";
    
    public static final String HEADER_TITLE = "type";
    public static final String TRANSACTION = "transaction";
    public static final String SECURITIES_FOR_BRANCH = "secforbranch";

    /**
     * Checks if xlis exchange contains transaction or allowed securities for branch.
     */
    public void isTransaction(Exchange msg) {
        String xlis = (String) msg.getIn().getBody();

        Pattern pattern = Pattern.compile(SEC_FOR_BRANCHES_REGEX, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(xlis);

        if(matcher.find()) {
            msg.getOut().setHeader(HEADER_TITLE, SECURITIES_FOR_BRANCH);
            msg.getOut().setBody(xlis, String.class);
        }
        else {
            msg.getOut().setHeader(HEADER_TITLE, TRANSACTION);
            msg.getOut().setBody(xlis, String.class);
        }
    }

}
