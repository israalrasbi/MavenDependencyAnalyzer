package com.isra.security.dependency_analyzer.DTOs;

import com.isra.security.dependency_analyzer.Entities.Project;
import com.isra.security.dependency_analyzer.Utils.HelperUtils;

import java.util.ArrayList;

public class SBOMRequest {
    private Integer id;
    private String projectName;
    private String bomJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public static SBOMRequest convertToDTO(Project project, String bomJsonContent){
        SBOMRequest dto = new SBOMRequest();
        if(HelperUtils.isNotNull(project)){
            dto.setId(project.getId());
            dto.setProjectName(project.getProjectName());
            dto.setBomJson(bomJsonContent);
        }
        return dto;
    }

    public static Project convertToEntity(SBOMRequest dto){
        Project entity = new Project();
        if(HelperUtils.isNotNull(dto)){
            entity.setId(dto.getId());
            entity.setProjectName(dto.getProjectName());
            entity.setDependencyList(new ArrayList<>());
        }
        return entity;
    }
}
