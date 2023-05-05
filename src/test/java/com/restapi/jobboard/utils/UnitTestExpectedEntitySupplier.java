package com.restapi.jobboard.utils;

import com.restapi.jobboard.model.entity.JobType;
import com.restapi.jobboard.model.entity.Tag;
import com.restapi.jobboard.model.entity.Vacancy;
import com.restapi.jobboard.model.payload.response.LocationStatistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitTestExpectedEntitySupplier {

    public static Vacancy createVacancyEntity() {
        return new Vacancy()
                .setId(123L)
                .setSlug("java-developer-444444")
                .setCompanyName("Soft")
                .setTitle("Java Developer")
                .setDescription("Many text here")
                .setRemote(true)
                .setUrl("/http/some/url")
                .setTags(new ArrayList<>(Arrays.asList(
                        new Tag()
                                .setId(1L)
                                .setTag("Software Development"),
                        new Tag()
                                .setId(2L)
                                .setTag("e-commerce")
                )))
                .setJobTypes(new ArrayList<>(Arrays.asList(
                        new JobType()
                                .setId(1L)
                                .setType("Full Time"),
                        new JobType()
                                .setId(2L)
                                .setType("Permanent")

                )))
                .setLocation("Dusseldorf")
                .setCreatedAt(1111111111L)
                .setViews(6);
    }

    public static List<Vacancy> createVacancyList() {

        return List.of(
                new Vacancy(1L, "java-developer-555555", "Soft-new", "Java Developer",
                        "Many text here again", true, "/http/some/url/5",
                        new ArrayList<>(Arrays.asList(
                                new Tag(1L, "Software Development"), new Tag(2L, "e-commerce"))),
                        new ArrayList<>(Arrays.asList(
                                new JobType(1L, "Full Time"),
                                new JobType(2L, "Permanent"))), "Berlin", 2222222222L, 32),


                new Vacancy(2L, "java-developer-666666", "Soft-idea", "Java Developer",
                        "Many text here again", true, "/http/some/url/6",
                        new ArrayList<>(Arrays.asList(
                                new Tag(1L, "Software Development"), new Tag(2L, "e-commerce"))),
                        new ArrayList<>(Arrays.asList(
                                new JobType(1L, "Full Time"),
                                new JobType(2L, "Permanent"))), "Munich", 3333333333L, 19));
    }

    public static List<LocationStatistics> getLocationStatistics() {
        return new ArrayList<>(Arrays.asList(
                new LocationStatistics(createVacancyEntity().getLocation(), 3),
                new LocationStatistics(createVacancyList().get(0).getLocation(), 2),
                new LocationStatistics(createVacancyList().get(1).getLocation(), 1)
        ));
    }
}
