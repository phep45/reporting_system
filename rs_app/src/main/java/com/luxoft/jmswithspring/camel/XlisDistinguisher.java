package com.luxoft.jmswithspring.camel;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Qualifier("xlisDistinguisher")
public class XlisDistinguisher {
    private static final String SEC_FOR_BRANCHES_REGEX = "<securities_branches>.*</securities_branches>";

    /**
     * Checks if xlis string contains transaction or allowed securities for branch.
     *
     * @param xlis string containing xlis message
     * @return true if is transaction </br>
     * false if is securities for branch
     */
    public boolean isTransaction(String xlis) {
        Pattern pattern = Pattern.compile(SEC_FOR_BRANCHES_REGEX, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(xlis);

        return !matcher.find();
    }

}
