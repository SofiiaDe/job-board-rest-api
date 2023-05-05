package com.restapi.jobboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown by system in case there is an attempt to save to the database a vacancy which is already stored there.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class VacancyAlreadyStoredException extends RuntimeException {

    public VacancyAlreadyStoredException(String errorMessage) {
        super(errorMessage);
    }

}
