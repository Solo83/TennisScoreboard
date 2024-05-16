package com.solo83.tennisscoreboard.utils.validator;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;

import java.util.Map;
import java.util.regex.Pattern;

public class PlayerNameValidator {

    private final String PLAYER_PATTERN = "^[A-Za-z]+ [A-Za-z]+$";
    private final Pattern pattern = Pattern.compile(PLAYER_PATTERN);

    public boolean validate(Map<String, String[]> parameterMap, String parameterName) throws ValidatorException {

        String[] parameterValues = parameterMap.get(parameterName);

        if (parameterValues == null || parameterValues.length == 0) {
            throw new ValidatorException("Required parameter '" + parameterName + "' is missing");
        }

        String parameterValue = parameterValues[0];

        if (parameterValue.trim().isEmpty() || !pattern.matcher(parameterValue).find()) {
            throw new ValidatorException("Value '" + parameterValue + "' is not valid for '" + parameterName + "'");
        }

        return true;
    }

}
