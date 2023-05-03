package com.restapi.jobboard.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LinksModel {

    private String first;
    private String last;
    private String prev;
    private String next;
}
