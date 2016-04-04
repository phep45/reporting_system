package com.luxoft.jmswithspring.manager;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DateConverter {

    private static final Logger log = LoggerFactory.getLogger(DateConverter.class);

    private static final String US_DATE_REGEX = "^(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-(19|20)\\d\\d$";

    /**
     * Converts date from MM-dd-yyyy (US format) to dd/MM/yyyy (ISO format)
     *
     * @param dateInUsFormat date to convert
     * @return date in dd/MM/yyyy format
     */
    public String usToIso(String dateInUsFormat) {
        Preconditions.checkArgument(isInUsFormat(dateInUsFormat), "Date must be in US format.");

        String isoDate = dateInUsFormat;

        SimpleDateFormat usFormat = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat isoFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = usFormat.parse(isoDate);
            isoDate = isoFormat.format(date);
        } catch (ParseException e) {
            log.info("Unable to parse {} date.", dateInUsFormat);
        }

        return isoDate;
    }

    private boolean isInUsFormat(String date) {
        Pattern pattern = Pattern.compile(US_DATE_REGEX);
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }
}
