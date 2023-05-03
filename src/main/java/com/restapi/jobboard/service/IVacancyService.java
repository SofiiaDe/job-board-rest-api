package com.restapi.jobboard.service;

import com.restapi.jobboard.model.JobBoardModel;
import com.restapi.jobboard.model.dto.VacancyDto;

import java.util.List;

public interface IVacancyService {

    List<VacancyDto> getAllVacancies();
    VacancyDto getVacancyById(Long id);
    JobBoardModel getJobBoard();
}
