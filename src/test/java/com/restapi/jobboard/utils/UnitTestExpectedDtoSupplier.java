package com.restapi.jobboard.utils;

import com.restapi.jobboard.model.dto.JobTypeDto;
import com.restapi.jobboard.model.dto.TagDto;
import com.restapi.jobboard.model.dto.VacancyDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitTestExpectedDtoSupplier {

    public static VacancyDto createVacancyDto() {
        return new VacancyDto()
                .setId(123L)
                .setSlug("java-developer-444444")
                .setCompanyName("Soft")
                .setTitle("Java Developer")
                .setDescription("Many text here")
                .setRemote(true)
                .setUrl("/http/some/url")
                .setTags(new ArrayList<>(Arrays.asList(
                        new TagDto()
                                .setId(1L)
                                .setTag("Software Development"),
                        new TagDto()
                                .setId(2L)
                                .setTag("e-commerce")
                )))
                .setJobTypes(new ArrayList<>(Arrays.asList(
                        new JobTypeDto()
                                .setId(1L)
                                .setType("Full Time"),
                        new JobTypeDto()
                                .setId(2L)
                                .setType("Permanent")

                )))
                .setLocation("Dusseldorf")
                .setCreatedAt(1111111111)
                .setViews(6);
    }

    public static List<VacancyDto> createVacancyDtoList() {

        return List.of(
                new VacancyDto(1L, "java-developer-555555", "Soft-new", "Java Developer",
                        "Many text here again", true, "/http/some/url/5",
                        new ArrayList<>(Arrays.asList(
                                new TagDto(1L, "Software Development"), new TagDto(2L, "e-commerce"))),
                        new ArrayList<>(Arrays.asList(
                                new JobTypeDto(1L, "Full Time"),
                                new JobTypeDto(2L, "Permanent"))), "Berlin", 2222222222L, 32),


                new VacancyDto(2L, "java-developer-666666", "Soft-idea", "Java Developer",
                        "Many text here again", true, "/http/some/url/6",
                        new ArrayList<>(Arrays.asList(
                                new TagDto(1L, "Software Development"), new TagDto(2L, "e-commerce"))),
                        new ArrayList<>(Arrays.asList(
                                new JobTypeDto(1L, "Full Time"),
                                new JobTypeDto(2L, "Permanent"))), "Munich", 3333333333L, 19));
    }

}
