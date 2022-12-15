package com.esgc.EntityProfile.API.APIModels.ESGMateriality;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ESGMaterlityDriverSummaryAPIWrapper {

    private ArrayList<ESGMaterlityDriverSummaryDetails> drivers;
    private ArrayList<String> materiality_types;
    private String methodology;

}
