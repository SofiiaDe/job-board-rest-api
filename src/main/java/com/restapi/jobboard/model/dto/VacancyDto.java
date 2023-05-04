package com.restapi.jobboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class VacancyDto {

    private Long id;
    private String slug;
    private String companyName;
    private String title;
    private String description;
    private boolean remote;
    private String url;
    private List<TagDto> tags;
    private List<JobTypeDto> jobTypes;
    private String location;
    private String createdAt;
    private long vacancyCount;

}
