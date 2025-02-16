document.addEventListener("DOMContentLoaded", function () {
    const uploadBtn = document.getElementById("uploadBtn");
    const generateFeedbackBtn = document.getElementById("generateFeedbackBtn");
    const displayDashboardBtn = document.getElementById("displayDashboardBtn");
    const dropbox = document.getElementById("dropbox");
    const fileInput = document.getElementById("fileInput");
    const loadingSpinner = document.getElementById("loadingSpinner");
    const vulnerabilitiesList = document.getElementById("vulnerabilitiesList");
    const aiFeedback = document.getElementById("aiFeedback");
    
    let pomFilePath = "";
  
    // Allow drag and drop
    dropbox.addEventListener("click", () => fileInput.click());
  
    function allowDrop(event) {
      event.preventDefault();
    }
  
    function drop(event) {
      event.preventDefault();
      const files = event.dataTransfer.files;
      if (files.length > 0) {
        fileInput.files = files;
        pomFilePath = files[0].path;
      }
    }
  
    uploadBtn.addEventListener("click", () => {
      if (!pomFilePath) {
        alert("Please upload a pom.xml file first.");
        return;
      }
  
      loadingSpinner.style.display = "block";
      generateFeedbackBtn.disabled = true;
  
      fetch(`http://localhost:9090/dependencyAnalyzer/generateSbom?path=${pomFilePath}`)
        .then(response => response.text())
        .then(sbom => {
          return fetch(`http://localhost:9090/track/uploadSbom?projectUuid=some-uuid&filePath=${pomFilePath}`, {
            method: "POST",
          });
        })
        .then(response => response.json())
        .then(data => {
          return fetch(`http://localhost:9090/track/getVulnerabilities?projectUuid=some-uuid`);
        })
        .then(response => response.json())
        .then(vulnerabilities => {
          loadingSpinner.style.display = "none";
          generateFeedbackBtn.disabled = false;
          displayVulnerabilities(vulnerabilities);
        })
        .catch(error => {
          loadingSpinner.style.display = "none";
          alert("An error occurred: " + error.message);
        });
    });
  
    generateFeedbackBtn.addEventListener("click", () => {
      if (!pomFilePath) {
        alert("Please upload a pom.xml file first.");
        return;
      }
  
      aiFeedback.style.display = "none";
  
      fetch(`http://localhost:9090/ai/analyzeVulnerabilities?filePath=${pomFilePath}`)
        .then(response => response.text())
        .then(aiResponse => {
          aiFeedback.innerText = aiResponse;
          aiFeedback.style.display = "block";
        })
        .catch(error => {
          aiFeedback.style.display = "none";
          alert("An error occurred: " + error.message);
        });
    });
  
    displayDashboardBtn.addEventListener("click", () => {
      window.open("http://localhost:8080/dashboard", "_blank");
    });
  
    function displayVulnerabilities(vulnerabilities) {
      vulnerabilitiesList.innerHTML = "";
      vulnerabilities.forEach(vulnerability => {
        const li = document.createElement("li");
        li.textContent = `CVE: ${vulnerability.cve}, Severity: ${vulnerability.severity}`;
        vulnerabilitiesList.appendChild(li);
      });
    }
  });
  