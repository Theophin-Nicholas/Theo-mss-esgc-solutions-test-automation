package com.esgc.PortfolioAnalysis.API.APIModels;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class LeadersAndLaggardsWrapper {

    @SerializedName("leaders")
    private List<LeadersAndLaggards> leaders;

    @SerializedName("laggards")
    private List<LeadersAndLaggards> laggards;
}
