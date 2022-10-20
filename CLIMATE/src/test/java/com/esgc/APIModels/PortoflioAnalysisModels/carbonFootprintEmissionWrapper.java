package com.esgc.APIModels.PortoflioAnalysisModels;

import lombok.Data;

import java.util.ArrayList;


@Data
public class carbonFootprintEmissionWrapper {

    public String category;
    public ArrayList<carbonFootprintEmissionData> data;
}
