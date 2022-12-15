package com.esgc.PortfolioAnalysis.DB.DBModels;

import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import lombok.Data;
import lombok.ToString;

@Data @ToString(callSuper=true)
public class EnergyTransitionManagement extends ResearchLineIdentifier {

    private Double GS_ENERGY_TRANSITION_LEADERSHIP;
    private Double GS_ENERGY_TRANSITION_IMPLEMENTATION;
    private Double GS_ENERGY_TRANSITION_RESULTS;
    private Double GS_ENERGY_TRANSITION_TOTAL;

}
