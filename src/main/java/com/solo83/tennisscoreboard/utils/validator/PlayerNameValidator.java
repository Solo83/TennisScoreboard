package com.solo83.tennisscoreboard.utils.validator;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerNameValidator {

    private final String PLAYER_PATTERN = "^[A-Za-z]+ [A-Za-z]+$";
    private final Pattern pattern = Pattern.compile(PLAYER_PATTERN);

    public boolean validate(Map<String, String[]> parameterMap, String parameterName) throws ValidatorException {

        if (!parameterMap.containsKey(parameterName)) {
            throw new ValidatorException("Required parameter '" + parameterName + "' is missing");
        } else {
            String parameterValue = parameterMap.get(parameterName)[0];
            Matcher matcher = pattern.matcher(parameterValue);
            if (parameterValue.trim().isEmpty() || !matcher.find()) {
                throw new ValidatorException("Value '" + parameterValue + "' is not valid for '" + parameterName + "'");
            }
        }
        return true;
    }

}
