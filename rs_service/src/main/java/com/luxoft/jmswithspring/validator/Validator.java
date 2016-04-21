package com.luxoft.jmswithspring.validator;

import org.springframework.stereotype.Component;

@Component
public interface Validator<T> {

    boolean validate(T t);

}
