package com.restapi.jobboard.service;

import com.restapi.jobboard.exception.VacancyAlreadyStoredException;
import com.restapi.jobboard.exception.VacancyNotFoundException;
import com.restapi.jobboard.model.arbeitnowapi.ApiVacancyModel;
import com.restapi.jobboard.model.arbeitnowapi.JobBoardModel;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.model.entity.Vacancy;
import com.restapi.jobboard.model.mapper.ApiVacancyMapper;
import com.restapi.jobboard.model.mapper.JobTypeMapper;
import com.restapi.jobboard.model.mapper.TagMapper;
import com.restapi.jobboard.model.mapper.VacancyMapper;
import com.restapi.jobboard.repository.JobTypeRepository;
import com.restapi.jobboard.repository.TagRepository;
import com.restapi.jobboard.repository.VacancyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class VacancyService implements IVacancyService {

    private static final String JOB_BOARD_API_URL = "https://www.arbeitnow.com/api/job-board-api";
    private static final String VACANCIES_PAGE_API_URL = "https://www.arbeitnow.com/api/job-board-api?page=";
    private static final String VACANCY_IS_ALREADY_STORED_IN_DB = "The database already contains the vacancy with the following slug: ";
    private static final String VACANCY_NOT_FOUND = "Can't retrieve vacancy with id = ";


    private final VacancyRepository vacancyRepository;
    private final TagRepository tagRepository;
    private final JobTypeRepository jobTypeRepository;
    private final RestTemplate restTemplate;
    private final ApiVacancyMapper apiVacancyMapper;
    private final VacancyMapper vacancyMapper;
    private final TagMapper tagMapper;
    private final JobTypeMapper jobTypeMapper;

    @Override
    public JobBoardModel getJobBoard() {
        return restTemplate.getForObject(JOB_BOARD_API_URL, JobBoardModel.class);
    }

    @Override
    public List<VacancyDto> getVacanciesPage(int page) {
        JobBoardModel jobBoardModel = restTemplate.getForObject(VACANCIES_PAGE_API_URL + page, JobBoardModel.class);
        List<ApiVacancyModel> apiVacancyModelList = jobBoardModel == null ? Collections.emptyList() : jobBoardModel.getData();
        return apiVacancyModelList.stream().map(apiVacancyMapper::toEntity).toList();
    }

    @Override
    public VacancyDto saveVacancy(VacancyDto vacancyDto) {
        if (slugExist(vacancyDto.getSlug())) {
            log.error(VACANCY_IS_ALREADY_STORED_IN_DB + vacancyDto.getSlug());
            throw new VacancyAlreadyStoredException(VACANCY_IS_ALREADY_STORED_IN_DB + vacancyDto.getSlug());
        }

        tagRepository.saveAll(vacancyDto.getTags().stream().map(tagMapper::toEntity).toList());
        jobTypeRepository.saveAll(vacancyDto.getJobTypes().stream().map(jobTypeMapper::toEntity).toList());

        return vacancyMapper.toDto(vacancyRepository.save(vacancyMapper.toEntity(vacancyDto)));
    }

    @Override
    public List<VacancyDto> getAllVacanciesFromDb() {
        return vacancyRepository.findAll().stream()
                .map(vacancyMapper::toDto)
                .toList();
    }

    @Override
    public VacancyDto getVacancyById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException(VACANCY_NOT_FOUND + id));

        return vacancyMapper.toDto(vacancy);
    }

    private boolean slugExist(String slug) {
        return vacancyRepository.findVacancyBySlug(slug).isPresent();
    }
}
