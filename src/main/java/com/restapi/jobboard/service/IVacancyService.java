package com.restapi.jobboard.service;

import com.restapi.jobboard.model.arbeitnowapi.JobBoardModel;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.model.payload.request.SearchRequest;
import com.restapi.jobboard.model.payload.response.LocationStatistics;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVacancyService {

    List<VacancyDto> getAllVacanciesFromDb();
    VacancyDto getVacancyById(Long id);
    JobBoardModel getJobBoard();
    List<VacancyDto> getVacanciesPage(int page);
    VacancyDto saveVacancy(VacancyDto vacancyDto);
    Page<VacancyDto> searchVacancy(SearchRequest request);
    List<VacancyDto> getTop10LatestMostViewed();
    List<LocationStatistics> getLocationStatistics();
}
