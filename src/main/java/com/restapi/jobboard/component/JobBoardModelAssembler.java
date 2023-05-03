package com.restapi.jobboard.component;

import com.restapi.jobboard.controller.VacancyController;
import com.restapi.jobboard.model.dto.VacancyDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JobBoardModelAssembler implements
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
                linkTo(methodOn(VacancyController.class).getJobBoard()).withRel("vacancies"));
    }

}
