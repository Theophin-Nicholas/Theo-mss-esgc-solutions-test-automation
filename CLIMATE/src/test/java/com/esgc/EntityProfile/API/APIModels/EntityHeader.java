package com.esgc.EntityProfile.API.APIModels;


import lombok.Data;

import java.util.List;

@Data

public class EntityHeader {
    private String company_name;
    private String country;
    private String generic_sector;
    private String lei;
    private String mesg_sector;
    private String mesg_sector_detail_description;
    private String methodology;
    private String model_version;
    private String orbis_id;
    private String primary_isin;
    private String  overall_disclosure_score ;
    private String region;
    private int research_line_id;
    private String sector_l1;
    private String sector_l2;
    private List<EntityHeaderSubsidiaryDetails> subsidiaries;
    private int tot_subs;
}
