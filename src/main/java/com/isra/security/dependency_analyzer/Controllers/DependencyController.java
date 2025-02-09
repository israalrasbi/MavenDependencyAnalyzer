package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.DTOs.SBOMRequest;
import com.isra.security.dependency_analyzer.DTOs.VulnerabilityResponse;
import com.isra.security.dependency_analyzer.Services.DependencyService;
import com.isra.security.dependency_analyzer.Services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping(value = "dependency-analyzer")
public class DependencyController {
    @Autowired
    private DependencyService dependencyService;

    @Autowired
    private TrackService trackService;

    @PostMapping("generateBom")
    public ResponseEntity<String> generateBom(@RequestBody SBOMRequest sbomRequest){
        try {
            // Call the service to generate BOM file from POM
            File bomFile = dependencyService.generateBomJson(sbomRequest);

            // Return the path of the BOM file or any success message
            return ResponseEntity.ok("BOM file generated successfully: " + bomFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating BOM file: " + e.getMessage());
        }
    }


}
