package com.restapi.jobboard.controller;

import com.restapi.jobboard.component.VacancyModelAssembler;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.model.payload.request.SearchRequest;
import com.restapi.jobboard.service.IVacancyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/job-board")
@AllArgsConstructor
public class VacancyController {


    private final IVacancyService vacancyService;
    private final VacancyModelAssembler vacancyModelAssembler;

    @GetMapping
    public ResponseEntity<?> getJobBoard() {
        return ResponseEntity.ok(vacancyService.getJobBoard());
    }

    @GetMapping("/page")
    public CollectionModel<EntityModel<VacancyDto>> getVacanciesPage(@RequestParam(required = false, defaultValue = "1") int page) {

        List<EntityModel<VacancyDto>> vacancies = vacancyService.getVacanciesPage(page).stream()
                .map(vacancyModelAssembler::toModel)
                .toList();

        return CollectionModel.of(vacancies, linkTo(methodOn(VacancyController.class).getVacanciesPage(page)).withSelfRel());
    }

    @PostMapping("/savePage")
    CollectionModel<EntityModel<VacancyDto>> saveVacanciesPage(@RequestParam(required = false, defaultValue = "1") int page) {

        List<VacancyDto> vacancyDtoList = vacancyService.getVacanciesPage(page);
        List<EntityModel<VacancyDto>> savedVacancies = vacancyDtoList.stream()
                .map(vacancy -> vacancyModelAssembler.toModel(vacancyService.saveVacancy(vacancy))).toList();

        return CollectionModel.of(savedVacancies, linkTo(methodOn(VacancyController.class).getAllStoredVacancies()).withSelfRel());
    }

    @GetMapping("/vacancies")
    public CollectionModel<EntityModel<VacancyDto>> getAllStoredVacancies() {

        List<EntityModel<VacancyDto>> vacancies = vacancyService.getAllVacanciesFromDb().stream()
                .map(vacancyModelAssembler::toModel)
                .toList();

        return CollectionModel.of(vacancies, linkTo(methodOn(VacancyController.class).getAllStoredVacancies()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<VacancyDto> getVacancyById(@PathVariable Long id) {

        VacancyDto vacancyDto = vacancyService.getVacancyById(id);
        return vacancyModelAssembler.toModel(vacancyDto);
    }

    @PostMapping(value = "/search")
    public Page<VacancyDto> search(@RequestBody SearchRequest request) {
        return vacancyService.searchVacancy(request);
    }

    @GetMapping("/topVisited")
    public CollectionModel<EntityModel<VacancyDto>> getTop10LatestMostViewed() {
        List<EntityModel<VacancyDto>> vacancies = vacancyService.getTop10LatestMostViewed().stream()
                .map(vacancyModelAssembler::toModel)
                .toList();

        return CollectionModel.of(vacancies, linkTo(methodOn(VacancyController.class).getAllStoredVacancies()).withSelfRel());
    }

    @GetMapping("/locationStatistics")
    public ResponseEntity<?> getLocationStatistics() {
        return ResponseEntity.ok(vacancyService.getLocationStatistics());
    }

}
