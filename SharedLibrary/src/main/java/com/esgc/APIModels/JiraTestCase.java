package com.esgc.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JiraTestCase {
    private String testKey;
    private String status;
    private List<TestEvidence> evidences;

    public JiraTestCase(String e, String status) {
        this.testKey = e;
        this.status = status;
    }

}
