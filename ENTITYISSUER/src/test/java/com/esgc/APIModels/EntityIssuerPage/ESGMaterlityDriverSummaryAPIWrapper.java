package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ESGMaterlityDriverSummaryAPIWrapper {

    private ArrayList<ESGMaterlityDriverSummaryDetails> drivers;
    private ArrayList<String> materiality_types;
    private String methodology;

}
