package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.ArrayList;


@Data
public class ScoreAllocation {
        private ArrayList<DriverDetail> driver_details;
        private int driver_count;
}




