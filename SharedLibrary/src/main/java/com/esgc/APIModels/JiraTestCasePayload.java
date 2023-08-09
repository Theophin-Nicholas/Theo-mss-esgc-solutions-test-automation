package com.esgc.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class JiraTestCasePayload {
    public String testExecutionKey;
    public List<JiraTestCase> tests;
}
