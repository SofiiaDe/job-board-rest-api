package com.restapi.jobboard;

import com.restapi.jobboard.exception.VacancyAlreadyStoredException;
import com.restapi.jobboard.exception.VacancyNotFoundException;
import com.restapi.jobboard.exception.VacancyProcessingException;
import com.restapi.jobboard.model.arbeitnowapi.JobBoardModel;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.model.entity.Vacancy;
import com.restapi.jobboard.model.mapper.*;
import com.restapi.jobboard.model.payload.response.LocationStatistics;
import com.restapi.jobboard.repository.JobTypeRepository;
import com.restapi.jobboard.repository.TagRepository;
import com.restapi.jobboard.repository.VacancyRepository;
import com.restapi.jobboard.service.IVacancyService;
import com.restapi.jobboard.service.VacancyService;
import com.restapi.jobboard.utils.UnitTestExpectedDtoSupplier;
import com.restapi.jobboard.utils.UnitTestExpectedEntitySupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacancyServiceTest {

    private static final String VACANCY_IS_ALREADY_STORED_IN_DB = "The database already contains the vacancy with the following slug: ";
    private static final String VACANCY_NOT_FOUND = "Can't retrieve vacancy with id = ";
    private static final String CAN_NOT_RECEIVE_LOCATION_STATISTICS = "Can't receive statistics on location and number of vacancies";
    private static final Long NOT_EXIST_ID = -1L;
    private static final Long DEFAULT_VACANCY_ID = 123L;

    @Mock
    private VacancyRepository vacancyRepositoryMock;
    @Mock
    private TagRepository tagRepositoryMock;
    @Mock
    private JobTypeRepository jobTypeRepositoryMock;
    @Mock
    private RestTemplate restTemplateMock;
    private VacancyMapper vacancyMapper;
    private ApiVacancyMapper apiVacancyMapper;
    private TagMapper tagMapper;
    private JobTypeMapper jobTypeMapper;
    private IVacancyService vacancyService;
    private VacancyDto vacancyDto;
    private Vacancy expectedVacancy;
    private List<VacancyDto> vacancyDtoList;
    private List<Vacancy> vacancyList;

    @BeforeEach
    public void setUp() {
        tagMapper = new TagMapperImpl();
        jobTypeMapper = new JobTypeMapperImpl();
        vacancyMapper = new VacancyMapperImpl(tagMapper, jobTypeMapper);
        apiVacancyMapper = new ApiVacancyMapperImpl();

        vacancyService = new VacancyService(vacancyRepositoryMock, tagRepositoryMock, jobTypeRepositoryMock, restTemplateMock,
                apiVacancyMapper, vacancyMapper, tagMapper, jobTypeMapper);
        vacancyDto = UnitTestExpectedDtoSupplier.createVacancyDto();
        expectedVacancy = UnitTestExpectedEntitySupplier.createVacancyEntity();
        vacancyDtoList = UnitTestExpectedDtoSupplier.createVacancyDtoList();
        vacancyList = UnitTestExpectedEntitySupplier.createVacancyList();
    }

    @Test
    void getJobBoardTest_shouldReturnJobBoard() {
        assertNotNull(restTemplateMock);
        JobBoardModel jobBoardModel = new JobBoardModel();
        when(restTemplateMock.getForObject(anyString(), any())).thenReturn(jobBoardModel);

        Object actual = vacancyService.getJobBoard();
        assertNotNull(actual);
        assertEquals(jobBoardModel.getClass(), actual.getClass());
    }

    @Test
    void testGetAllVacanciesFromDb_ShouldReturnVacancyDtoList() {
        when(vacancyRepositoryMock.findAll()).thenReturn(vacancyList);

        List<VacancyDto> actual = vacancyService.getAllVacanciesFromDb();

        verify(vacancyRepositoryMock).findAll();
        assertEquals(2, actual.size());
        assertEquals(actual, vacancyDtoList);
    }

    @Test
    void testGetAllVacanciesFromDb_whenCalled_callsRepo() {
        vacancyService.getAllVacanciesFromDb();
        verify(vacancyRepositoryMock, times(1)).findAll();
    }

    @Test
    void testSaveVacancy_whenRepoThrows_throwsException() {
        Optional<Vacancy> vacancyOptional = Optional.of(vacancyMapper.toEntity(vacancyDto));
        when(vacancyRepositoryMock.findVacancyBySlug(vacancyDto.getSlug())).thenReturn(vacancyOptional);
        assertThrowsExactly(VacancyAlreadyStoredException.class, () -> vacancyService.saveVacancy(vacancyDto));
    }

    @Test
    void testSaveVacancy_whenThrowsVacancyStoredException_shouldShowExceptionMessage() {
        String messageNotToGet = "aaaaa";
        Optional<Vacancy> vacancyOptional = Optional.of(vacancyMapper.toEntity(vacancyDto));
        when(vacancyRepositoryMock.findVacancyBySlug(vacancyDto.getSlug())).thenReturn(vacancyOptional);

        VacancyAlreadyStoredException exception = assertThrows(VacancyAlreadyStoredException.class,
                () -> vacancyService.saveVacancy(vacancyDto));
        assertEquals(VACANCY_IS_ALREADY_STORED_IN_DB + vacancyDto.getSlug(), exception.getMessage());
        assertNotEquals(messageNotToGet, exception.getMessage());

    }

    @Test
    void testSaveVacancy_whenCalled_callsRepo() throws VacancyAlreadyStoredException {
        vacancyService.saveVacancy(vacancyDto);
        verify(vacancyRepositoryMock, times(1)).save(any(Vacancy.class));
    }

    @Test
    void saveVacancyTest_ShouldReturnVacancyDto() {
        when(vacancyRepositoryMock.findVacancyBySlug(vacancyDto.getSlug())).thenReturn(Optional.empty());
        when(vacancyRepositoryMock.save(vacancyMapper.toEntity(vacancyDto))).thenReturn(expectedVacancy);

        VacancyDto result = vacancyService.saveVacancy(vacancyDto);
        assertEquals(vacancyDto.getId(), result.getId());
        assertEquals(vacancyDto.getSlug(), result.getSlug());
        assertEquals(vacancyDto.getCompanyName(), result.getCompanyName());
        assertEquals(vacancyDto.getTitle(), result.getTitle());
        assertEquals(vacancyDto.getDescription(), result.getDescription());
        assertEquals(vacancyDto.isRemote(), result.isRemote());
        assertEquals(vacancyDto.getUrl(), result.getUrl());
        assertEquals(vacancyDto.getTags().get(0).getTag(), result.getTags().get(0).getTag());
        assertEquals(vacancyDto.getJobTypes().get(1).getType(), result.getJobTypes().get(1).getType());
        assertEquals(vacancyDto.getLocation(), result.getLocation());
        assertEquals(vacancyDto.getCreatedAt(), result.getCreatedAt());
        assertEquals(vacancyDto.getViews(), result.getViews());

        verify(vacancyRepositoryMock).findVacancyBySlug(vacancyDto.getSlug());
        verify(vacancyRepositoryMock).save(expectedVacancy);
    }

    @Test
    void testGetVacancyById_whenCalled_RepoCalled() {
        Vacancy vacancy = vacancyMapper.toEntity(vacancyDto);
        when(vacancyRepositoryMock.findById(any())).thenReturn(Optional.of(vacancy));
        vacancyService.getVacancyById(DEFAULT_VACANCY_ID);
        verify(vacancyRepositoryMock, times(1)).findById(DEFAULT_VACANCY_ID);
    }

    @Test
    void testGetVacancyById_whenRepoThrows_throwsException() {
        when(vacancyRepositoryMock.findById(NOT_EXIST_ID)).thenThrow(
                new VacancyNotFoundException(VACANCY_NOT_FOUND + NOT_EXIST_ID));
        assertThrowsExactly(VacancyNotFoundException.class, () -> vacancyService.getVacancyById(NOT_EXIST_ID));
    }

    @Test
    void testGetVacancyById_whenThrowsNotFoundException_ShouldShowExceptionMessage() {
        String messageNotToGet = "aaaaa";
        long vacancyId = 1125L;

        when(vacancyRepositoryMock.findById(vacancyId)).thenReturn(Optional.empty());

        VacancyNotFoundException exception = assertThrows(VacancyNotFoundException.class,
                () -> vacancyService.getVacancyById(vacancyId));
        assertEquals(VACANCY_NOT_FOUND + vacancyId, exception.getMessage());
        assertNotEquals(messageNotToGet, exception.getMessage());
    }

    @Test
    void testGetVacancyById_whenCalled_returnsCorrectVacancy() {
        long vacancyId = 123L;
        when(vacancyRepositoryMock.findById(vacancyId)).thenReturn(
                Optional.of(vacancyMapper.toEntity(vacancyDto)));
        when(vacancyRepositoryMock.save(any())).thenReturn(vacancyMapper.toEntity(vacancyDto));

        VacancyDto result = vacancyService.getVacancyById(vacancyId);
        assertEquals(vacancyDto.getId(), result.getId());
        assertEquals(vacancyDto.getSlug(), result.getSlug());
        assertEquals(vacancyDto.getCompanyName(), result.getCompanyName());
        assertEquals(vacancyDto.getTitle(), result.getTitle());
        assertEquals(vacancyDto.getDescription(), result.getDescription());
        assertEquals(vacancyDto.isRemote(), result.isRemote());
        assertEquals(vacancyDto.getUrl(), result.getUrl());
        assertEquals(vacancyDto.getTags().get(0).getTag(), result.getTags().get(0).getTag());
        assertEquals(vacancyDto.getJobTypes().get(1).getType(), result.getJobTypes().get(1).getType());
        assertEquals(vacancyDto.getLocation(), result.getLocation());
        assertEquals(vacancyDto.getCreatedAt(), result.getCreatedAt());
        assertEquals(vacancyDto.getViews(), result.getViews());
    }

    @Test
    void getVacancyByIdTest_WhenAccessed_ShouldUpdateViewsField() {
        when(vacancyRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expectedVacancy));
        when(vacancyRepositoryMock.save(any(Vacancy.class))).thenAnswer(i -> i.getArguments()[0]);

        VacancyDto actualDto = vacancyService.getVacancyById(expectedVacancy.getId());
        verify(vacancyRepositoryMock).findById(expectedVacancy.getId());
        verify(vacancyRepositoryMock).save(any(Vacancy.class));
        assertEquals(7, actualDto.getViews());
    }

    @Test
    void testGetLocationStatistics_WhenStatisticsReceived_RepoCalled() {
        List<LocationStatistics> locationStatistics = UnitTestExpectedEntitySupplier.getLocationStatistics();
        when(vacancyRepositoryMock.getLocationStatistics()).thenReturn(locationStatistics);
        vacancyService.getLocationStatistics();
        verify(vacancyRepositoryMock, times(1)).getLocationStatistics();
    }

    @Test
    void testGetLocationStatistics_WhenVacancyNotFound_ShouldThrowException() {
        doThrow(VacancyNotFoundException.class)
                .when(vacancyRepositoryMock)
                .getLocationStatistics();
        assertThrows(VacancyProcessingException.class, () -> vacancyService.getLocationStatistics());
    }

    @Test
    void testGetLocationStatistics_whenThrowsVacancyNotFoundException_shouldShowExceptionMessage() {
        String messageNotToGet = "aaaaa";
        doThrow(VacancyNotFoundException.class)
                .when(vacancyRepositoryMock)
                .getLocationStatistics();

        VacancyProcessingException exception = assertThrows(VacancyProcessingException.class,
                () -> vacancyService.getLocationStatistics());

        assertEquals(CAN_NOT_RECEIVE_LOCATION_STATISTICS, exception.getMessage());
        assertNotEquals(messageNotToGet, exception.getMessage());
    }

}
