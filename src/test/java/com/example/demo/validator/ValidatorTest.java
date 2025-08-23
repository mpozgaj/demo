package com.example.demo.validator;

import org.junit.jupiter.api.Test;

import static com.example.demo.validator.Validator.isValidName;
import static com.example.demo.validator.Validator.isValideOib;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {

    @Test
    void nameTest() {
        isValidName("bla", "name");
        isValidName("test", "name");
        isValidName("Test", "name");
    }

    @Test
    void nameExceptionTest() {
        assertThrows(RuntimeException.class, () -> {
            isValidName("1234567", "name");
        });
        assertThrows(RuntimeException.class, () -> {
            isValidName("M M", "name");
        });
        assertThrows(RuntimeException.class, () -> {
            isValidName(" name", "name");
        });
        assertThrows(RuntimeException.class, () -> {
            isValidName("#name", "name");
        });
        assertThrows(RuntimeException.class, () -> {
            isValidName(";", "name");
        });
    }

    @Test
    void oibTest() {
        isValideOib("12345678901");
        isValideOib("11111111111");
    }

    @Test
    void oibExceptionTest() {
        assertThrows(RuntimeException.class, () -> {
            isValideOib("123456789012");
        });
        assertThrows(RuntimeException.class, () -> {
            isValideOib("1");
        });
        assertThrows(RuntimeException.class, () -> {
            isValideOib("123456789 0");
        });
        assertThrows(RuntimeException.class, () -> {
            isValideOib("123456789a0");
        });
    }
}
