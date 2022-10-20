package com.esgc.APIModels.PortoflioAnalysisModels;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;


@Data
public class SectorSummaryWrapper {
    @SerializedName(value=" ")
    private List<SectorSummary> sectors;
}
