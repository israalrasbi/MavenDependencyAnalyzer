package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.DTOs.SBOMRequest;
import com.isra.security.dependency_analyzer.DTOs.VulnerabilityResponse;
import com.isra.security.dependency_analyzer.Services.DependencyService;
import com.isra.security.dependency_analyzer.Services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "dependency-analyzer")
public class DependencyController {
    @Autowired
    private DependencyService dependencyService;
    private TrackService trackService;

    @PostMapping("analyze")
    public List<VulnerabilityResponse> analyzeDependencies(@RequestBody SBOMRequest sbomRequest){
        String bomJson = dependencyService.generateBomJson(sbomRequest);
        List<VulnerabilityResponse> vulnerabilities = trackService.analyzeBOM(bomJson);
        return vulnerabilities;
    }


}
