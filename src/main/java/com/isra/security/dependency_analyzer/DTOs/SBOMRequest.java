package com.isra.security.dependency_analyzer.DTOs;

public class SBOMRequest {
    private String projectName;
    private String bomJson;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBomJson() {
        return bomJson;
    }

    public void setBomJson(String bomJson) {
        this.bomJson = bomJson;
    }
}
