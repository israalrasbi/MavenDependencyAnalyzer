package com.isra.security.dependency_analyzer.Interfaces;

import com.isra.security.dependency_analyzer.DTOs.VulnerabilityResponse;

import java.util.List;

public interface DependencyTrackClientInterface {
    //method to send the bom.json to Dependency-Track and receive results
    List<VulnerabilityResponse> analyze(String bomJson);

    //method to get the vulnerability list by project name
    List<VulnerabilityResponse> getVulnerabilitiesByProjectName(String projectName);
}
