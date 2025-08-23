package com.example.demo.validator;

import com.example.demo.exception.InputException;

public interface Validator {

    static void isValidName(final String str, final String fieldName) {
        final String expression = "[a-zA-Z]+\\.?";
        if(!str.matches(expression)) {
            throw new InputException("Invalid " + fieldName);
        }
    }

    static void isValideOib(final String str) {
        final String expression = "[0-9]{11}";
        if(!str.matches(expression)) {
            throw new InputException("Invalid OIB");
        }
    }
}
