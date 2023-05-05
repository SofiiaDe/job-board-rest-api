package com.restapi.jobboard;

import com.restapi.jobboard.component.VacancyModelAssembler;
import com.restapi.jobboard.controller.VacancyController;
import com.restapi.jobboard.exception.VacancyNotFoundException;
import com.restapi.jobboard.exception.VacancyProcessingException;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.service.IVacancyService;
import com.restapi.jobboard.utils.UnitTestExpectedDtoSupplier;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class VacancyRestControllerTest {

    private static final String JOB_BOARD_ENDPOINT = "/job-board";
    private static final Long DEFAULT_VACANCY_ID = 123L;
    private static final Long NOT_EXIST_ID = -1L;
    private MockMvc mockMvc;
    @Mock
    private IVacancyService vacancyService;
    @InjectMocks
    private VacancyController vacancyController;
    @Spy
    private VacancyModelAssembler vacancyModelAssembler;
    private VacancyDto vacancyDto;
    private List<VacancyDto> vacancyDtoList;
    private String jsonVacancy;

    @BeforeEach
    public void setUp() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(vacancyController).build();
        vacancyDto = UnitTestExpectedDtoSupplier.createVacancyDto();
        vacancyDtoList = UnitTestExpectedDtoSupplier.createVacancyDtoList();
        jsonVacancy = readJsonWithFile();
    }

    @Test
    void getVacancyByIdTest_givenExistingVacancy_whenVacancyRequested_thenResourceRetrieved() throws Exception {
        given(this.vacancyService.getVacancyById(DEFAULT_VACANCY_ID))
                .willReturn(vacancyDto);

        this.mockMvc.perform(get(JOB_BOARD_ENDPOINT + "/" + DEFAULT_VACANCY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").doesNotExist())
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void getVacancyByIdTest_WhenNotFound() throws Exception {
        doThrow(new VacancyNotFoundException("some message")).when(vacancyService).getVacancyById(NOT_EXIST_ID);

        mockMvc
                .perform(get(JOB_BOARD_ENDPOINT + "/{id}", NOT_EXIST_ID))
                .andExpect(status().isNotFound());
        verify(vacancyService).getVacancyById(NOT_EXIST_ID);
    }

    @Test
    void getAllStoredVacanciesTest() throws Exception {
        when(vacancyService.getAllVacanciesFromDb()).thenReturn(vacancyDtoList);

        mockMvc
                .perform(
                        get(JOB_BOARD_ENDPOINT + "/vacancies")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vacancyService).getAllVacanciesFromDb();
    }

    @Test
    void saveVacanciesPageTest_shouldInvokeServiceMethodSaveVacancy() throws Exception {
        mockMvc.perform(
                        post(JOB_BOARD_ENDPOINT + "/savePage")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonVacancy))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void getVacancyByIdTest_ShouldUpdateViewsField() throws Exception {
        when(vacancyService.getVacancyById(DEFAULT_VACANCY_ID)).thenReturn(vacancyDto);

        mockMvc.perform(
                        get(JOB_BOARD_ENDPOINT + "/" + DEFAULT_VACANCY_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.slug", is("java-developer-444444")))
                .andExpect(jsonPath("$.companyName", is("Soft")))
                .andExpect(jsonPath("$.title", is("Java Developer")))
                .andExpect(jsonPath("$.description", is("Many text here")))
                .andExpect(jsonPath("$.remote", is(true)))
                .andExpect(jsonPath("$.views", is(6)));
    }

    @Test
    void getTop10LatestMostViewedTest_ShouldReturnTop10() throws Exception {

        when(vacancyService.getTop10LatestMostViewed()).thenReturn(vacancyDtoList);

        mockMvc.perform(
                        get(JOB_BOARD_ENDPOINT + "/topVisited")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getLocationStatisticsTest_WhenFound() throws Exception {
        mockMvc
                .perform(get(JOB_BOARD_ENDPOINT + "/locationStatistics"))
                .andExpect(status().is2xxSuccessful());

        verify(vacancyService).getLocationStatistics();
        verifyNoMoreInteractions(vacancyService);
    }

    @Test
    void getLocationStatisticsTest_WhenNotFound() throws Exception {
        doThrow(new VacancyProcessingException("some message")).when(vacancyService).getLocationStatistics();

        mockMvc
                .perform(get(JOB_BOARD_ENDPOINT + "/locationStatistics"))
                .andExpect(status().isBadRequest());
        verify(vacancyService).getLocationStatistics();
    }

    private String readJsonWithFile() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("VacancyJSON.json");
        assert inputStream != null;
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

}
