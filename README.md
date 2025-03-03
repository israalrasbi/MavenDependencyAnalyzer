# Maven Dependency Analyzer

![Maven Dependency Analyzer](https://img.shields.io/badge/Spring%20Boot-3.0.0-green.svg) ![Dependency-Track](https://img.shields.io/badge/Dependency--Track-4.8.2-blue.svg) ![Spring%20AI](https://img.shields.io/badge/Spring%20AI-1.0.0-yellow.svg)

## 🚀 Overview
Maven Dependency Analyzer is a powerful tool designed to analyze `pom.xml` files, generate Software Bill of Materials (SBOM), and detect vulnerabilities in dependencies. It integrates with **Dependency-Track** for vulnerability assessment and uses **Spring AI** to suggest fixes for vulnerabilities.

## 🎯 Features
- 📜 **Extract Dependencies**: Reads `pom.xml` and identifies dependencies.
- 🔍 **Vulnerability Detection**: Sends SBOM (`bom.json`) to Dependency-Track and retrieves vulnerability reports.
- 🧠 **AI-Powered Fix Suggestions**: Uses **Spring AI** to analyze vulnerabilities and provide recommendations.
- 📊 **Custom Frontend**: Visual representation of dependencies and vulnerabilities.

## 📌 Usage
1. **Upload `pom.xml`** using `DependencyController` to extract dependencies.
2. **Generate `bom.json`** for SBOM analysis.
3. **Send `bom.json`** to Dependency-Track for vulnerability detection.
4. **Retrieve vulnerabilities** using `TrackController`.
5. **AI-Powered Fixes**: AI suggests solutions for vulnerabilities.


