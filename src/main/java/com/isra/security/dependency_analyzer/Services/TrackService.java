package com.isra.security.dependency_analyzer.Services;

import com.isra.security.dependency_analyzer.DTOs.VulnerabilityResponse;
import com.isra.security.dependency_analyzer.Interfaces.DependencyTrackClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {
    @Autowired
    DependencyTrackClientInterface dependencyTrackClientInterface;

    public List<VulnerabilityResponse> analyzeBOM(String bomJson){
        return dependencyTrackClientInterface.analyze(bomJson);
    }

    public List<VulnerabilityResponse> getVulnerabilities(String projectName){
        return dependencyTrackClientInterface.getVulnerabilitiesByProjectName(projectName);
    }
}
