package com.myibltest.stepdef;

import io.cucumber.java.ParameterType;

/**
 * Custom parameter type definitions for Cucumber
 */
public class ParameterTypes {

    /**
     * Define a boolean parameter type for Cucumber expressions
     * @param value the string value "true" or "false"
     * @return a boolean value
     */
    @ParameterType("true|false")
    public Boolean bool(String value) {
        return Boolean.valueOf(value);
    }
}
