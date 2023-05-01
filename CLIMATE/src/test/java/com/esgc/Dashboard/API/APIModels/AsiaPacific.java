package com.esgc.Dashboard.API.APIModels;

import lombok.Data;

import java.util.ArrayList;
@Data
public class AsiaPacific {
    public ArrayList<Entity> entities;
    public int remaining_entities;
    public int total_entities;
}
