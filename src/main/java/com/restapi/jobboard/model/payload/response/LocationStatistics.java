package com.restapi.jobboard.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class LocationStatistics {

    private String location;
    private long vacanciesNumber;

}
