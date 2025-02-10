package com.isra.security.dependency_analyzer.Services;

import org.cyclonedx.BomGeneratorFactory;
import org.cyclonedx.CycloneDxSchema;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.cyclonedx.generators.json.BomJsonGenerator;
import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Component;


@Service
public class SBOMGeneratorService {
    public String generateSBOM(String pomFilePath) throws IOException {
        try {
            // Create a BOM object
            Bom bom = new Bom();

            // Add dependencies manually (since PomParser doesn't exist)
            // Here, you'd parse pom.xml to extract dependencies

            // Use the BomJsonGeneratorFactory to create the generator
            BomJsonGenerator bomJsonGenerator = (BomJsonGenerator) BomGeneratorFactory.createJson(CycloneDxSchema.Version.VERSION_14, bom);
            String bomJson = bomJsonGenerator.toJsonString();

            // Save SBOM to a file
            File sbomFile = new File("bom.json");
            try (FileWriter writer = new FileWriter(sbomFile)) {
                writer.write(bomJson);
            }

            return bomJson; // Return the generated SBOM
        } catch (Exception e) {
            throw new IOException("Failed to generate SBOM from pom.xml at path: " + pomFilePath, e);
        }
    }
}
