package com.isra.security.dependency_analyzer.Interfaces;

import org.springframework.stereotype.Component;

import java.io.File;

//@Component
public interface SBOMGeneratorInterface {
    //method to generate bom.json from pom.xml
    File generateSBOMFromPOM(String pomXmlPath);
}
