package com.isra.security.dependency_analyzer.Services;

import com.isra.security.dependency_analyzer.DTOs.SBOMRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DependencyService {


    @Autowired
    SBOMGeneratorService sbomGeneratorService;

    public File generateBomJson(SBOMRequest request){
        return sbomGeneratorService.generateSBOMFromPOM(request.getPomXml());
    }

}
