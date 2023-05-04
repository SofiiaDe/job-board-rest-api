package com.restapi.jobboard.controller;

import com.restapi.jobboard.component.VacancyModelAssembler;
import com.restapi.jobboard.model.dto.VacancyDto;
import com.restapi.jobboard.service.IVacancyService;
import lombok.AllArgsConstructor;
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

        return CollectionModel.of(savedVacancies, linkTo(methodOn(VacancyController.class).getVacancies()).withSelfRel());
    }

    @GetMapping("/vacancies")
    public CollectionModel<EntityModel<VacancyDto>> getVacancies() {

        List<EntityModel<VacancyDto>> vacancies = vacancyService.getAllVacancies().stream()
                .map(vacancyModelAssembler::toModel)
                .toList();

        return CollectionModel.of(vacancies, linkTo(methodOn(VacancyController.class).getVacancies()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<VacancyDto> getVacancyById(@PathVariable Long id) {

        VacancyDto vacancyDto = vacancyService.getVacancyById(id);
        return vacancyModelAssembler.toModel(vacancyDto);
    }


}
