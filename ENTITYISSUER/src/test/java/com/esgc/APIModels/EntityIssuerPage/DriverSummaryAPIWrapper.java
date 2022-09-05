package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DriverSummaryAPIWrapper {

    private ArrayList<DriverSummaryCriterion> criteria;

}
