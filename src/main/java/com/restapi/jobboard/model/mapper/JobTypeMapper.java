package com.restapi.jobboard.model.mapper;

import com.restapi.jobboard.model.dto.JobTypeDto;
import com.restapi.jobboard.model.entity.JobType;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class JobTypeMapper implements EntityMapper<JobTypeDto, JobType> {

}
