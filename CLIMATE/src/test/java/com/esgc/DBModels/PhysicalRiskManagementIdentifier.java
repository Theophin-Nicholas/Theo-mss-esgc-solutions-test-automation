package com.esgc.DBModels;

import lombok.Data;
import lombok.ToString;

@Data @ToString(callSuper=true)
public class PhysicalRiskManagementIdentifier extends ResearchLineIdentifier{

    private Double GS_PH_RISK_MGT_LEADERSHIP;
    private Double GS_PH_RISK_MGT_IMPLEMENTATION;
    private Double GS_PH_RISK_MGT_RESULTS;
    private Double GS_PH_RISK_MGT_TOTAL;

}
