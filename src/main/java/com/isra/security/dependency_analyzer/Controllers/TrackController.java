package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping("/uploadSbom")
    public ResponseEntity<String> uploadSbom(@RequestParam String projectUuid, @RequestParam String filePath) {
        return trackService.uploadSbomFromFile(projectUuid, filePath);
    }

    @GetMapping("/getVulnerabilities")
    public ResponseEntity<List<Map<String, Object>>> getVulnerabilities(@RequestParam String projectUuid) {
        return ResponseEntity.ok(trackService.getVulnerabilities(projectUuid));
    }
}
