package com.esgc.ONDEMAND.API.APIModels;


import lombok.Data;

import java.util.ArrayList;

@Data

public class Request {
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

