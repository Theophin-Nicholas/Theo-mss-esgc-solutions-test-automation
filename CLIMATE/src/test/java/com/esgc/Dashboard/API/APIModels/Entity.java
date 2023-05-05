package com.esgc.Dashboard.API.APIModels;

import lombok.Data;

@Data
public class Entity {
    public String bvd9_number;
    public String company_name;
    public Object parent_bvd9_number;
    public Object parent_company_name;
    public String managed_type;
    public String region_country;
    public String sector;
    public double perc_investment;
    public int controversies_critical;
    public int controversies_total;
}
