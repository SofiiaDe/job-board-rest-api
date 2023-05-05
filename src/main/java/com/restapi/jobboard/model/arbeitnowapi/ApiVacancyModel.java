package com.restapi.jobboard.model.arbeitnowapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiVacancyModel {

    private String slug;
    @JsonProperty("company_name")
    private String companyName;
    private String title;
    private String description;
    private boolean remote;
    private String url;
    private List<String> tags;
    @JsonProperty("job_types")
    private List<String> jobTypes;
    private String location;
    @JsonProperty("created_at")
    private long createdAt;

}
