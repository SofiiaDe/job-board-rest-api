package com.restapi.jobboard.model.arbeitnowapi;

import lombok.Data;

import java.util.List;

@Data
public class JobBoardModel {

    private List<ApiVacancyModel> data;
    private LinksModel links;
    private MetaModel meta;
}
