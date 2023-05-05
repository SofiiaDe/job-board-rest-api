package com.restapi.jobboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VacancyProcessingException extends RuntimeException {

    public VacancyProcessingException(String errorMessage) {
        super(errorMessage);
    }

}
