package com.restapi.jobboard.model;

import lombok.Data;

import java.util.List;

@Data
public class ArbeitnowApiVacancyModel {

    private String slug;
    private String company_name;
    private String title;
    private String description;
    private boolean remote;
    private String url;
    private List<String> tags;
    private List<String> job_types;
    private String location;
    private String created_at;

}
