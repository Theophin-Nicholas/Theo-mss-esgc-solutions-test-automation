package com.esgc.PortfolioAnalysis.API.APIModels;

import lombok.Data;

import java.util.ArrayList;


@Data
public class carbonFootprintEmissionWrapper {

    public String category;
    public ArrayList<carbonFootprintEmissionData> data;
}
