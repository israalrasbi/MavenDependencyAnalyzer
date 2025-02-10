package com.isra.security.dependency_analyzer.Services;

import org.cyclonedx.BomGeneratorFactory;
import org.cyclonedx.CycloneDxSchema;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.cyclonedx.generators.json.BomJsonGenerator;
import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Component;


@Service
public class SBOMGeneratorService {
    public String generateSBOM(String pomFilePath) throws IOException {
        try {
            // Read and parse the pom.xml
            File pomFile = new File(pomFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(pomFile);
            doc.getDocumentElement().normalize();

            // Extract dependencies
            NodeList dependencies = doc.getElementsByTagName("dependency");
            Bom bom = new Bom();

            for (int i = 0; i < dependencies.getLength(); i++) {
                String groupId = dependencies.item(i).getChildNodes().item(1).getTextContent();
                String artifactId = dependencies.item(i).getChildNodes().item(3).getTextContent();
                String version = dependencies.item(i).getChildNodes().item(5).getTextContent();

                // Create CycloneDX component
                Component component = new Component();
                component.setType(Component.Type.LIBRARY);
                component.setGroup(groupId);
                component.setName(artifactId);
                component.setVersion(version);
                component.setPurl("pkg:maven/" + groupId + "/" + artifactId + "@" + version);

                // Add component to BOM
                bom.addComponent(component);
            }

            // Generate SBOM JSON
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
