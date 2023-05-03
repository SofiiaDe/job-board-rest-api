package com.restapi.jobboard.service;

import com.restapi.jobboard.model.JobBoardModel;
import com.restapi.jobboard.model.dto.VacancyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class VacancyService implements IVacancyService {

    private static final String JOB_BOARD_API_URL = "https://www.arbeitnow.com/api/job-board-api";

    private final RestTemplate restTemplate;

    @Override
    public List<VacancyDto> getAllVacancies() {
        VacancyDto[] jobBoardData = restTemplate.getForObject(JOB_BOARD_API_URL, VacancyDto[].class);
        return jobBoardData == null ? Collections.emptyList() : Arrays.asList(jobBoardData);
    }

    @Override
    public VacancyDto getVacancyById(Long id) {
        return null;
    }

    @Override
    public JobBoardModel getJobBoard() {
        return restTemplate.getForObject(JOB_BOARD_API_URL, JobBoardModel.class);
    }
}
