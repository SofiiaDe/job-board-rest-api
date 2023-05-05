package com.restapi.jobboard.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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
    private long createdAt;
    private long views;

}
