package com.restapi.jobboard.component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.restapi.jobboard.controller.VacancyController;
import com.restapi.jobboard.model.dto.VacancyDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class VacancyModelAssembler implements
        RepresentationModelAssembler<VacancyDto, EntityModel<VacancyDto>> {

    /**
     * Converts Vacancy objects to EntityModel<Vacancy> objects.
     *
     * @param vacancyDto Object of Vacancy type
     * @return EntityModel<Vacancy>
     */
    @Override
    public EntityModel<VacancyDto> toModel(VacancyDto vacancyDto) {

        return EntityModel.of(vacancyDto,
                linkTo(methodOn(VacancyController.class).getVacancyById(vacancyDto.getId())).withSelfRel(),
                linkTo(methodOn(VacancyController.class).getVacancies()).withRel("vacancies"));
    }

}
