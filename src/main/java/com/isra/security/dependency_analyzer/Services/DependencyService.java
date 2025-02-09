package com.isra.security.dependency_analyzer.Services;

import com.isra.security.dependency_analyzer.DTOs.SBOMRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DependencyService {


    @Autowired
    SBOMGeneratorService sbomGeneratorService;

    public File generateBomJson(SBOMRequest request) throws Exception{
        File bomFile = sbomGeneratorService.generateSBOMFromPOM(request.getPomXml());
        if (!bomFile.exists() || bomFile.length() == 0) {
            throw new RuntimeException("BOM file generation failed: File is empty or not created.");
        }

        return bomFile;
    }

}
