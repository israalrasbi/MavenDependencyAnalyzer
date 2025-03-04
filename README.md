# Maven Dependency Analyzer

![Maven Dependency Analyzer](https://img.shields.io/badge/Spring%20Boot-3.0.0-green.svg) ![Dependency-Track](https://img.shields.io/badge/Dependency--Track-4.8.2-blue.svg) ![Spring%20AI](https://img.shields.io/badge/Spring%20AI-1.0.0-yellow.svg)

## 🚀 Overview
Maven Dependency Analyzer is a powerful tool designed to analyze `pom.xml` files, generate Software Bill of Materials (SBOM), and detect vulnerabilities in dependencies. It integrates with **Dependency-Track** for vulnerability assessment and uses **Spring AI** to suggest fixes for vulnerabilities.

## 🎯 Features
- 📜 **Extract Dependencies**: Reads `pom.xml` and identifies dependencies.
- 🔍 **Vulnerability Detection**: Sends SBOM (`bom.json`) to Dependency-Track and retrieves vulnerability reports.
- 🧠 **AI-Powered Fix Suggestions**: Uses **Spring AI** to analyze vulnerabilities and provide recommendations.
- 📊 **Custom Frontend**: Visual representation of dependencies and vulnerabilities.

## 🏗️ Project Structure

```
├── src/main/java/com/mavenanalyzer
│   ├── entity/          # Project, Dependency, Vulnerability, User
│   ├── dto/             # SBOMRequest, VulnerabilityResponse, SignInRequestDTO, SignUpRequestDTO
│   ├── interfaces/      # DependencyTrackClientInterface, SBOMGeneratorInterface, UserRepository
│   ├── service/         # DependencyService, TrackService, AIService, AuthService, CustomUserDetailsService
│   ├── controller/      # DependencyController, TrackController, AIController, AuthController
│   ├── config/          # SecurityConfig
├── frontend/            # Custom frontend 
├── README.md            # Project documentation
```

## 🛠️ Installation

### Prerequisites

Ensure you have the following installed:

- **Java 17+**
- **Maven**
- **Docker** (for running Dependency-Track)
- **Spring Boot**
- **Dependency-Track API**

## 📌 Usage

1. **Upload pom.xml** using `DependencyController` to extract dependencies.
2. **Generate SBOM**  using `DependencyController` for SBOM analysis.
3. **Send SBOM**`` to Dependency-Track using `TrackController`for vulnerability detection.
4. **Retrieve vulnerabilities** using `TrackController`.
5. **Provide AI-Driven Fixes** using `AIController`.

## 🚀 API Endpoints

| Method | Endpoint                     | Description                                 |
| ------ | ---------------------------- | ------------------------------------------- |
| `POST` | `/dependencyAnalyzer/uploadPom`     | Uploads `pom.xml` and extracts dependencies |
| `GET` | `/dependencyAnalyzer/generateSbom`         | Generate SBOM (`bom.json`) |
| `POST` | `/track/uploadSbom`         | Sends SBOM (`bom.json`) to Dependency-Track |
| `GET`  | `track/getVulnerabilities` | Retrieves vulnerability reports             |
| `POST` | `/ai/analyzeVulnerabilities`            | AI suggests fixes for vulnerabilities       |







