package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DriverSummaryCriterion {

    public ArrayList<DriverSummaryDrivers> drivers;
    public String weight_category;

}
