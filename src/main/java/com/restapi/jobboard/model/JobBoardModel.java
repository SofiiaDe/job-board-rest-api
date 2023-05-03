package com.restapi.jobboard.model;

import lombok.Data;

import java.util.List;

@Data
public class JobBoardModel {

    private List<ArbeitnowApiVacancyModel> data;
    private LinksModel links;
    private MetaModel meta;
}
