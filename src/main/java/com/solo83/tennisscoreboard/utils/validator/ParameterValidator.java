package com.solo83.tennisscoreboard.utils.validator;

import com.solo83.tennisscoreboard.utils.exception.ValidatorException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterValidator {

    Matcher matcher;

    public boolean validate(Map<String, String[]> parameterMap, String parameterName, String expression) throws ValidatorException {

        if (!parameterMap.containsKey(parameterName)) {
            throw new ValidatorException("Required parameter '" + parameterName + "' is missing");
        } else {
            String parameterValue = parameterMap.get(parameterName)[0];
            matcher = validateParameterValue(parameterValue, expression);
            if (parameterValue.trim().isEmpty() || !matcher.find()) {
                throw new ValidatorException("Value '" + parameterValue + "' is not valid for '" + parameterName + "'");
            }
        }
        return true;
    }

    private Matcher validateParameterValue(String parameterValue, String expression) {
        Pattern pattern = Pattern.compile(expression);
        return pattern.matcher(parameterValue);
    }

}
