package com.esgc.EntityProfile.DB.DBModels;

import lombok.Data;
import lombok.ToString;

@Data @ToString(callSuper=true)
public class PhysicalScore  {

    private String facilities_exposed;
    private String highest_risk_hazard;
    private String hrh_risk_category;

}
