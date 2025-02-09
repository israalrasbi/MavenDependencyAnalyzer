package com.isra.security.dependency_analyzer.Services;

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
import java.util.ArrayList;
import java.util.List;

@Service
public class SBOMGeneratorService {

    public File generateSBOMFromPOM(String pomXmlPath) {
        //build the JSON string manually
        StringBuilder jsonOutput = new StringBuilder();
        //start JSON array
        jsonOutput.append("[\n");

        try {
            //parsing the XML
            File pomFile = new File(pomXmlPath);
            if (!pomFile.exists()) {
                throw new FileNotFoundException("POM file not found at: " + pomXmlPath);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(pomFile);
            //remove unnecessary whitespaces
            doc.getDocumentElement().normalize();

            //extract dependencies
            NodeList dependencies = doc.getElementsByTagName("dependency");
            if (dependencies.getLength() == 0) {
                throw new RuntimeException("No dependencies found in the POM file.");
            }


            //loop through the dependencies
            for (int i = 0; i < dependencies.getLength(); i++) {
                Node dependencyNode = dependencies.item(i);
                //check if the node is element, to avoid processing text nodes or comments
                if (dependencyNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element dependencyElement = (Element) dependencyNode;

                    //extract the data using getTagValue function
                    String groupId = getTagValue("groupId", dependencyElement);
                    String artifactId = getTagValue("artifactId", dependencyElement);
                    String version = getTagValue("version", dependencyElement);

                    //append dependency as JSON object
                    jsonOutput.append(String.format(
                            "  {\n    \"groupId\": \"%s\",\n    \"artifactId\": \"%s\",\n    \"version\": \"%s\"\n  }",
                            groupId, artifactId, version
                    ));

                    //add comma if it's not the last dependency
                    if (i < dependencies.getLength() - 1) {
                        jsonOutput.append(",");
                    }
                    jsonOutput.append("\n");
                }
            }
            //end JSON array
            jsonOutput.append("]");

            //write the output to a file
            File outputFile = new File("bom.json");
            try (FileWriter fileWriter = new FileWriter(outputFile)) {
                fileWriter.write(jsonOutput.toString());
            }

            System.out.println("SBOM saved to bom.json");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new File("bom.json");
    }

    //helper function to get text content from XML tag
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        //return null if missing
        return "N/A";
    }
}
