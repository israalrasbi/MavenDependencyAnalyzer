package com.isra.security.dependency_analyzer.Services;

import org.cyclonedx.BomGeneratorFactory;
import org.cyclonedx.CycloneDxSchema;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
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
            //read and parse pom.xml
            File pomFile = new File(pomFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(pomFile);
            //remove unnecessary whitespaces
            doc.getDocumentElement().normalize();

            //extract dependencies, and save it to a list
            NodeList dependencies = doc.getElementsByTagName("dependency");
            Bom bom = new Bom();

            //loop through the dependencies
            for (int i = 0; i < dependencies.getLength(); i++) {
                Node depNode = dependencies.item(i);
                NodeList depChildNodes = depNode.getChildNodes();

                String groupId = "";
                String artifactId = "";
                String version = "";

                //loop through the child nodes to find groupId, artifactId, and version
                for (int j = 0; j < depChildNodes.getLength(); j++) {
                    Node childNode = depChildNodes.item(j);
                    if (childNode.getNodeName().equals("groupId")) {
                        groupId = childNode.getTextContent();
                    } else if (childNode.getNodeName().equals("artifactId")) {
                        artifactId = childNode.getTextContent();
                    } else if (childNode.getNodeName().equals("version")) {
                        version = childNode.getTextContent();
                    }
                }

                //create CycloneDX component
                if (!groupId.isEmpty() && !artifactId.isEmpty() && !version.isEmpty()) {
                    Component component = new Component();
                    component.setType(Component.Type.LIBRARY);
                    component.setGroup(groupId);
                    component.setName(artifactId);
                    component.setVersion(version);
                    component.setPurl("pkg:maven/" + groupId + "/" + artifactId + "@" + version);

                    //add component to bom
                    bom.addComponent(component);
                }
            }

            //generate sbom json
            BomJsonGenerator bomJsonGenerator = (BomJsonGenerator) BomGeneratorFactory.createJson(CycloneDxSchema.Version.VERSION_14, bom);
            String bomJson = bomJsonGenerator.toJsonString();

            File sbomFile = new File("bom.json");
            try (FileWriter writer = new FileWriter(sbomFile)) {
                writer.write(bomJson);
            }

            return bomJson;
        } catch (Exception e) {
            throw new IOException("Failed to generate SBOM from pom.xml at path: " + pomFilePath, e);
        }
    }
}
