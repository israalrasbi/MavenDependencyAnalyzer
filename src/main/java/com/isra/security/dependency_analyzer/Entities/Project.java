package com.isra.security.dependency_analyzer.Entities;


import java.util.List;

public class Project {
    private String projectUuid;
    private List<Dependency> dependencyList;

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public List<Dependency> getDependencyList() {
        return dependencyList;
    }

    public void setDependencyList(List<Dependency> dependencyList) {
        this.dependencyList = dependencyList;
    }
}
