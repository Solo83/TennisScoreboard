package com.solo83.tennisscoreboard.utils.validator;

import com.solo83.tennisscoreboard.utils.exception.ValidatorException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerNameValidatorTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    PlayerNameValidator validator = new PlayerNameValidator();

    @Test
    void PlayerNameWithTwoWordsShouldPassValidation () throws ValidatorException {
        when(request.getParameterMap()).thenReturn(new HashMap<>() {
            {
                put("player1", new String[]{"TwoWords Name"});
            }
        });

        assertTrue(validator.validate(request.getParameterMap(),"player1"));

    }

    @Test
    void PlayerNameWithOneWordShouldThrowException() {
        when(request.getParameterMap()).thenReturn(new HashMap<>() {
            {
                put("player2", new String[]{"NameWithOneWord"});
            }
        });

        Assertions.assertThrows(ValidatorException.class,
                () -> validator.validate(request.getParameterMap(),"player2"));
    }
}