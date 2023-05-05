package com.restapi.jobboard;

import com.restapi.jobboard.controller.VacancyController;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.service.IVacancyService;
import com.restapi.jobboard.utils.SpringTestConfig;
import com.restapi.jobboard.utils.UnitTestExpectedDtoSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestConfig.class)
public class ExternalApiInteractionTest {

    private static final String JOB_BOARD_API_URL = "https://www.arbeitnow.com/api/job-board-api";
    private static final String VACANCIES_PAGE_API_URL = "https://www.arbeitnow.com/api/job-board-api?page=";

    @MockBean
    private IVacancyService vacancyService;
    @Autowired
    private VacancyController vacancyController;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private VacancyDto vacancyDto;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        vacancyDto = UnitTestExpectedDtoSupplier.createVacancyDto();
    }

    @Test
    void getJobBoardTest_givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_thenReturnsMockedObject()
            throws URISyntaxException {

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(JOB_BOARD_API_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON));

        Object actual = vacancyController.getJobBoard();
        assertNotNull(actual);
    }

    @Test
    void getVacanciesPageTest_givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_thenReturnsMockedObject()
            throws URISyntaxException {

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(VACANCIES_PAGE_API_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON));

        Object actual = vacancyController.getVacanciesPage(10);
        assertNotNull(actual);
    }

    @Test
    void saveVacanciesPageTest_givenMockingIsDoneByMockRestServiceServer_whenIsCalled_thenReturnsMockedObject()
            throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(VACANCIES_PAGE_API_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON));

        when(vacancyService.saveVacancy(any())).thenReturn(vacancyDto);
        CollectionModel<EntityModel<VacancyDto>> actual = vacancyController.saveVacanciesPage(10);
        assertNotNull(actual);
    }
}
