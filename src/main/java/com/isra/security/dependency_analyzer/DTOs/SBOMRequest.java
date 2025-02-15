package com.isra.security.dependency_analyzer.DTOs;

import com.isra.security.dependency_analyzer.Entities.Project;
import com.isra.security.dependency_analyzer.Utils.HelperUtils;

import java.util.ArrayList;

public class SBOMRequest {
    private String projectUuid;
    private String bomJson;


    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getBomJson() {
        return bomJson;
    }

    public void setBomJson(String bomJson) {
        this.bomJson = bomJson;
    }

    public static SBOMRequest convertToDTO(Project project, String bomJsonContent, String projectUuid) {
        SBOMRequest dto = new SBOMRequest();
        if (HelperUtils.isNotNull(project)) {
            dto.setProjectUuid(projectUuid);
            dto.setBomJson(bomJsonContent);
        }
        return dto;
    }

    public static Project convertToEntity(SBOMRequest dto) {
        Project entity = new Project();
        if (HelperUtils.isNotNull(dto)) {
            entity.setDependencyList(new ArrayList<>());
        }
        return entity;
    }
}
