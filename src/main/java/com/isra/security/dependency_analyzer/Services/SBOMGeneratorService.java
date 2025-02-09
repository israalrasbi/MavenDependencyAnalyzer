package com.isra.security.dependency_analyzer.Services;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SBOMGeneratorService {

    public File generateSBOMFromPOM(String pomXmlPath) {
        try {
            File pomFile = new File(pomXmlPath);

            // Create a DocumentBuilderFactory and parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(pomFile);

            // Normalize the document
            doc.getDocumentElement().normalize();

            // Get all <dependency> nodes inside <dependencies>
            NodeList dependencies = doc.getElementsByTagName("dependency");

            List<String> dependencyList = new ArrayList<>();

            for (int i = 0; i < dependencies.getLength(); i++) {
                Node dependencyNode = dependencies.item(i);
                if (dependencyNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element dependencyElement = (Element) dependencyNode;

                    // Extract groupId, artifactId, and version
                    String groupId = getTagValue("groupId", dependencyElement);
                    String artifactId = getTagValue("artifactId", dependencyElement);
                    String version = getTagValue("version", dependencyElement);

                    // Print results
                    System.out.println("Dependency " + (i + 1) + ":");
                    System.out.println("  GroupId: " + groupId);
                    System.out.println("  ArtifactId: " + artifactId);
                    System.out.println("  Version: " + version);
                    System.out.println("-----------------------------------");

                    // Store in a list (optional)
                    dependencyList.add(groupId + ":" + artifactId + ":" + version);
                }
            }

            // Here, you can write the dependencies to bom.json if needed

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new File("bom.json");
    }

    // Helper function to get text content from XML tag
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "N/A"; // Return "N/A" if the tag is missing
    }

}
