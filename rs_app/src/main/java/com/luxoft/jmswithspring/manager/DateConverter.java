package com.luxoft.jmswithspring.manager;

import com.google.common.base.Preconditions;
import com.luxoft.jmswithspring.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DateConverter {

    public static final String US_DATE_REGEX = "^(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-(19|20)\\d\\d$";

    public String usToIso(String date) {
        Preconditions.checkArgument(isInUsFormat(date), "Date must be in US format.");
        String iso = null;

        String[] splitted = date.split("-");

        iso = splitted[1] +
                "/" +
                splitted[0] +
                "/" +
                splitted[2];

        return iso;
    }

    private boolean isInUsFormat(String date) {
        Pattern pattern = Pattern.compile(US_DATE_REGEX);
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

}
