package com.solo83.tennisscoreboard.utils.validator;
import com.solo83.tennisscoreboard.utils.exception.ValidatorException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class PlayerNameValidator {

    private final String PLAYER_PATTERN = "^[A-Za-z]+ [A-Za-z]+$";
    private final Pattern pattern = Pattern.compile(PLAYER_PATTERN);

    public boolean validate(Map<String, String[]> parameterMap, String parameterName) throws ValidatorException {

        String[] parameterValues = parameterMap.get(parameterName);

        if (parameterValues == null || parameterValues.length == 0) {
            log.error("Parameter is missing in request");
            throw new ValidatorException("Required parameter '" + parameterName + "' is missing");
        }

        String parameterValue = parameterValues[0];

        if (parameterValue.trim().isEmpty() || !pattern.matcher(parameterValue).find()) {
            log.error("Parameter value for {} is not valid", parameterName);
            throw new ValidatorException("The player's name must contain two words separated by a space, incorrect: " + parameterName);
        }

        log.info("Parameter value is VALID for {}", parameterName);
        return true;
    }

}
