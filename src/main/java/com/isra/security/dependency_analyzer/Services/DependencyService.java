package com.isra.security.dependency_analyzer.Services;

import com.isra.security.dependency_analyzer.DTOs.SBOMRequest;
import com.isra.security.dependency_analyzer.Interfaces.SBOMGeneratorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DependencyService {
    @Autowired
    SBOMGeneratorInterface sbomGeneratorInterface;

    public String generateBomJson(SBOMRequest sbomRequest){
        return sbomGeneratorInterface.generateSBOMFromPOM(sbomRequest.getBomJson());
    }

}
