package com.isra.security.dependency_analyzer.Interfaces;

public interface SBOMGeneratorInterface {
    //method to generate bom.json from pom.xml
    String generateSBOMFromPOM(String pomXml);
}
