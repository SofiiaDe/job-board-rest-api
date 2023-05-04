package com.restapi.jobboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TagDto {

    private Long id;
    private String tag;

}
