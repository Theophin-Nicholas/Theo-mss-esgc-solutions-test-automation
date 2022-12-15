package com.esgc.PortfolioAnalysis.DB.DBModels;

import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import lombok.Data;
import lombok.ToString;

@Data @ToString(callSuper=true)
public class TCFDIdentifier extends ResearchLineIdentifier {

    private Double GS_TCFD_STGY_LEADERSHIP;
    private Double GS_TCFD_STGY_IMPLEMENTATION;
    private Double GS_TCFD_STGY_RESULTS;
    private Double GS_TCFD_STGY_TOTAL;

}
