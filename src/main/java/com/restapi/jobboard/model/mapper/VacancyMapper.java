package com.restapi.jobboard.model.mapper;

import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.model.entity.Vacancy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring", uses = {TagMapper.class, JobTypeMapper.class})
@Component
public abstract class VacancyMapper implements EntityMapper<VacancyDto, Vacancy> {

}

