package com.esgc.APIModels.EntityPage;

import lombok.Data;

import java.util.List;

@Data
public class DriverScoreCriteria {

    private List<DriversData> drivers;
    private String weight_category;

}




