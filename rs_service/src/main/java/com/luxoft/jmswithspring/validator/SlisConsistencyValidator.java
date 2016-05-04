package com.luxoft.jmswithspring.validator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Qualifier("slisValidator")
public class SlisConsistencyValidator implements Validator<String> {
    private static final String VALID_SLIS = "\\d{15}.{28}\\d{8}(\\d{20}\\.\\d{11}\\/\\d{2}\\/\\d{8})+";

    @Override
    public boolean validate(String slis) {
        Pattern pattern = Pattern.compile(VALID_SLIS);
        Matcher matcher = pattern.matcher(slis);
        return matcher.find();
    }
}
