package com.esgc.APIModels.Dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeatMapWrapper {
    @JsonProperty(value = "HeatMap_Data")
    private List<HeatMapData> heatMap_Data;
    @JsonProperty(value = "x_axis_total_invct_pct")
    private List<HeatMapAxisData> x_axis_total_invct_pct;
    @JsonProperty(value = "y_axis_total_invct_pct")
    private List<HeatMapAxisData> y_axis_total_invct_pct;
}
