package com.esgc.EntityProfile.API.APIModels;

import lombok.Data;

import java.util.List;

@Data
public class SectorComparison {



    public String company_name ;

    public String company_score_category ;

    public String mesg_sector ;

    public List<SectorComparisonChartDetails> sector_distribution;

    public String sector_score_category ;

}
