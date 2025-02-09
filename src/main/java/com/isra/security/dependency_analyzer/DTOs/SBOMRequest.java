package com.isra.security.dependency_analyzer.DTOs;

import com.isra.security.dependency_analyzer.Entities.Project;
import com.isra.security.dependency_analyzer.Utils.HelperUtils;

import java.util.ArrayList;

public class SBOMRequest {
    private String projectName;
    private String pomXml;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPomXml() {
        return pomXml;
    }

    public void setPomXml(String pomXml) {
        this.pomXml = pomXml;
    }

    public static SBOMRequest convertToDTO(Project project, String bomJsonContent){
        SBOMRequest dto = new SBOMRequest();
        if(HelperUtils.isNotNull(project)){
            dto.setProjectName(project.getProjectName());
            dto.setPomXml(bomJsonContent);
        }
        return dto;
    }

    public static Project convertToEntity(SBOMRequest dto){
        Project entity = new Project();
        if(HelperUtils.isNotNull(dto)){
            entity.setProjectName(dto.getProjectName());
            entity.setDependencyList(new ArrayList<>());
        }
        return entity;
    }
}
