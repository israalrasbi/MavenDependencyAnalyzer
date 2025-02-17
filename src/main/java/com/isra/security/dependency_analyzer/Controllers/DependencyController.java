package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.SBOMGeneratorService;
import com.isra.security.dependency_analyzer.Services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping(value = "dependencyAnalyzer")
public class DependencyController {
    @Autowired
    private TrackService trackService;

    @Autowired
    private SBOMGeneratorService sbomGeneratorService;

    private static final String UPLOAD_DIR = "uploads/";
    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/generateSbom")
    public ResponseEntity<String> generateSBOM(@RequestParam("path") String pomFilePath) {
        try {
            //generate SBOM from the provided pom.xml path
            String sbom = sbomGeneratorService.generateSBOM(pomFilePath);
            return ResponseEntity.ok(sbom);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing pom.xml at path: " + pomFilePath);
        }
    }

    @PostMapping("/uploadPom")
    public ResponseEntity<String> uploadPomFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected for upload");
        }

        try {
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            File destFile = new File(uploadDir, file.getOriginalFilename());
            file.transferTo(destFile);

            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }


}
