package com.esgc.ONDEMAND.API.APIModels;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;

@Data

public class OnDemandRequests {
    public ArrayList<Request> requests;
    public Summary summary;
}

class Request {
    public ArrayList<Detail> details;
    public String id;
    public ArrayList<Status> status;
    public int total;
    public String create_datetime;
    public String create_date;
    public int eligible_entity_count;
    public int entity_request_count;
    public ScoreRange score_range;
    public String sector;
    public String location;
}

class Detail {
    public String bvd9_number;
    public String company_name;
    public String country_code;
    public String country_name;
    public int current_investment;
    public double investment_pct;
    public String region;
    public String region_name;
    public String score;
    public String score_quality;
    public String sector;
    public String state;
    public int actual_size;
    public String size;
    public String cancel_reason;

}

class ScoreRange {
    public int min;
    public int max;
}

class Status {
    public String label;
    public int value;
}

class Summary {
    public int entity_request_count;
    public double eligible_pct;
    public int company_count;
}
