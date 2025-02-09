package com.isra.security.dependency_analyzer.Entities;


import java.util.List;

public class Project {
    private String projectName;
    private List<Dependency> dependencyList;
    
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Dependency> getDependencyList() {
        return dependencyList;
    }

    public void setDependencyList(List<Dependency> dependencyList) {
        this.dependencyList = dependencyList;
    }
}
