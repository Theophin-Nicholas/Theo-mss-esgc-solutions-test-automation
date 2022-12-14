package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.List;


@Data
public class Header {
    private String company_name;
    private String country;
    private String lei;
    private String mesg_sector;
    private String mesg_sector_detail_description;
    private String methodology;
    private String model_version;
    private String research_line_id;
    private String orbis_id;
    private String generic_sector;
    private int overall_disclosure_score;
    private String primary_isin;
    private String region;
    private String sector_l2;
    private String sector_l1;
    private List<String> subsidiaries ;
    private int tot_subs;
}




