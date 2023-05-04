package com.restapi.jobboard.model.arbeitnowapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetaModel {

    @JsonProperty("current_page")
    private int currentPage;
    private long from;
    private String path;
    @JsonProperty("per_page")
    private int perPage;
    private long to;
    private String terms;
    private String info;

}
