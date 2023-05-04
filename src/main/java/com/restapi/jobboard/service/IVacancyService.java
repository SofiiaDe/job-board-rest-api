package com.restapi.jobboard.service;

import com.restapi.jobboard.model.arbeitnowapi.JobBoardModel;
import com.restapi.jobboard.model.dto.VacancyDto;

import java.util.List;

public interface IVacancyService {

    List<VacancyDto> getAllVacancies();
    VacancyDto getVacancyById(Long id);
    JobBoardModel getJobBoard();
    List<VacancyDto> getVacanciesPage(int page);
    VacancyDto saveVacancy(VacancyDto vacancyDto);

}
