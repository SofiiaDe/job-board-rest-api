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
    private final VacancyModelAssembler assembler;

    @GetMapping
    public ResponseEntity<?> getJobBoard() {
        return ResponseEntity.ok(vacancyService.getJobBoard());
    }

    @GetMapping("/page")
    public CollectionModel<EntityModel<VacancyDto>> getVacanciesPage(@RequestParam(required = false, defaultValue = "1") int page) {

        List<EntityModel<VacancyDto>> vacancies = vacancyService.getVacanciesPage(page).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(vacancies, linkTo(methodOn(VacancyController.class).getVacanciesPage(page)).withSelfRel());
    }

    @GetMapping("/vacancies")
    public CollectionModel<EntityModel<VacancyDto>> getVacancies() {

        List<EntityModel<VacancyDto>> vacancies = vacancyService.getAllVacancies().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(vacancies, linkTo(methodOn(VacancyController.class).getVacancies()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<VacancyDto> getVacancyById(@PathVariable Long id) {

        VacancyDto vacancyDto = vacancyService.getVacancyById(id);
        return assembler.toModel(vacancyDto);
    }

}
