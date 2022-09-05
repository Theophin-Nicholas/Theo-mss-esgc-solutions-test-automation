package com.esgc.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JiraTestCase {
    private String testKey;
    private String status;
}
