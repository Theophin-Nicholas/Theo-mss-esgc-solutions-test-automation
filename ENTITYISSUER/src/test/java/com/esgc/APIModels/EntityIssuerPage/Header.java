package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;


@Data
public class Header {
    private String company_name;
    private String country;
    private String lei;
    private String mesg_sector;
    private String mesg_sector_detail_description;
    private String orbis_id;
    private int overall_disclosure_score;
    private String primary_isin;
    private String region;
    private String sector_l2;
}




