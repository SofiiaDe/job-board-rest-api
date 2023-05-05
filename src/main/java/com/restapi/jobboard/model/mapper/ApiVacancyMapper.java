package com.restapi.jobboard.model.mapper;

import com.restapi.jobboard.model.arbeitnowapi.ApiVacancyModel;
import com.restapi.jobboard.model.dto.JobTypeDto;
import com.restapi.jobboard.model.dto.TagDto;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.model.entity.Tag;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class, JobTypeMapper.class})
@Component
public abstract class ApiVacancyMapper implements EntityMapper<ApiVacancyModel, VacancyDto> {

    List<TagDto> stringsToTagDtos(List<String> tags) {
        if (tags == null) {
            return null;
        }
        List<TagDto> list = new ArrayList<>(tags.size());
        tags.forEach(str -> list.add(new TagDto().setTag(str)));
        return list;
    }

    List<String> tagDtosToStrings(List<TagDto> tags) {
        if (tags == null) {
            return null;
        }
        List<String> list = new ArrayList<>(tags.size());
        tags.forEach(tag -> list.add(tag.getTag()));
        return list;
    }

    List<JobTypeDto> stringsToJobTypeDtos(List<String> jobTypes) {
        if (jobTypes == null) {
            return null;
        }

        List<JobTypeDto> list = new ArrayList<>(jobTypes.size());
        jobTypes.forEach(str -> list.add(new JobTypeDto().setType(str)));

        return list;
    }

    List<String> jobTypesToStrings(List<JobTypeDto> jobTypes) {
        if (jobTypes == null) {
            return null;
        }
        List<String> list = new ArrayList<>(jobTypes.size());
        jobTypes.forEach(jt -> list.add(jt.getType()));
        return list;
    }
}