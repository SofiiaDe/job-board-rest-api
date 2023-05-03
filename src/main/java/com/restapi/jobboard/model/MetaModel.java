package com.restapi.jobboard.model;

import lombok.Data;

@Data
public class MetaModel {

    private int current_page;
    private long from;
    private String path;
    private int per_page;
    private long to;
    private String terms;
    private String info;

}
