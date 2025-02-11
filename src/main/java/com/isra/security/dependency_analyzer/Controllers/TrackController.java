package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping("/upload-sbom")
    public ResponseEntity<String> uploadSbom(@RequestParam String projectUuid, @RequestBody String bomJson) {
        return trackService.uploadSbom(projectUuid, bomJson);
    }
}
