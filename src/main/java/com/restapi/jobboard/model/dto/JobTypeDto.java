package com.restapi.jobboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JobTypeDto {

    private Long id;
    private String type;

}
