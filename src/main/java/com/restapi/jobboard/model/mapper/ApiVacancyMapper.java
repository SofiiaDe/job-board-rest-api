package com.restapi.jobboard.model.mapper;

import com.restapi.jobboard.model.arbeitnowapi.ApiVacancyModel;
import com.restapi.jobboard.model.dto.VacancyDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class ApiVacancyMapper implements EntityMapper<ApiVacancyModel, VacancyDto> {

}

