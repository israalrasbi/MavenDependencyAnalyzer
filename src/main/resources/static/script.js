document.addEventListener("DOMContentLoaded", function () {
    const uploadBtn = document.getElementById("uploadBtn");
    const listVulnerabilitiesBtn = document.getElementById("listVulnerabilitiesBtn");
    const generateFeedbackBtn = document.getElementById("generateFeedbackBtn");
    const displayDashboardBtn = document.getElementById("displayDashboardBtn");
    const dropbox = document.getElementById("dropbox");
    const fileInput = document.getElementById("fileInput");
    const loadingSpinner = document.getElementById("loadingSpinner");
    const feedbackSpinner = document.getElementById("feedbackSpinner");
    const vulnerabilitiesList = document.getElementById("vulnerabilitiesList");
    const aiFeedback = document.getElementById("aiFeedback");
    const submitBtn = document.getElementById("submitBtn");

    let pomFile = null;

    dropbox.addEventListener("click", () => fileInput.click());
    uploadBtn.addEventListener("click", () => fileInput.click());
    fileInput.addEventListener("change", function () {
        if (this.files.length > 0) {
            pomFile = this.files[0];
            document.getElementById("dropbox-text").textContent = `Selected: ${pomFile.name}`;
            uploadBtn.style.display = "none";
            submitBtn.style.display = "block";
        }
    });

    //check if user is authenticated before allowing access
    document.addEventListener("DOMContentLoaded", function () {
        const token = localStorage.getItem("token");
        if (!token) {
            window.location.href = "signin.html"; //redirect to login if not authenticated
        }
    });


    //function to get the token
    function getToken() {
        return localStorage.getItem("token");
    }

    //upload pom.xml 
    submitBtn.addEventListener("click", async () => {
        const dropBox = document.getElementById("dropbox");
        if (!pomFile) {
            dropBox.innerHTML = `<p class="error-message">Please select a pom.xml file first.</strong></p>`;
            return;
        }

        try {
            const formData = new FormData();
            formData.append("file", pomFile);

            const token = getToken();
            const uploadResponse = await fetch(`${CONFIG.BASE_URL}/dependencyAnalyzer/uploadPom`, {
                method: "POST",
                body: formData,
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });

            if (!uploadResponse.ok) throw new Error("Failed to upload pom.xml");

            dropBox.innerHTML = `<p class="uploaded-message">File Uploaded: <strong>${pomFile.name}</strong></p>`;

            listVulnerabilitiesBtn.disabled = false;
        } catch (error) {
            dropBox.innerHTML = `<p class="error-message">An error occurred: <strong>${error.message}</strong></p>`;
        }
    });

    //generate sbom and fetch vulnerabilities
    listVulnerabilitiesBtn.addEventListener("click", async () => {
        vulnerabilitiesList.innerHTML = "";
        loadingSpinner.style.display = "block";
        listVulnerabilitiesBtn.disabled = true;
        generateFeedbackBtn.disabled = true;

        try {
            const token = getToken();
            //generate SBOM
            const sbomResponse = await fetch(`${CONFIG.BASE_URL}/dependencyAnalyzer/generateSbom?path=${CONFIG.pomFilePath}&projectUuid=${CONFIG.projectUuid}`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            if (!sbomResponse.ok) throw new Error("Failed to generate SBOM");

            //upload sbom to dependency track
            const uploadSbomResponse = await fetch(`${CONFIG.BASE_URL}/track/uploadSbom?projectUuid=${CONFIG.projectUuid}&filePath=${encodeURIComponent(CONFIG.bomFilePath)}`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            if (!uploadSbomResponse.ok) throw new Error("Failed to upload SBOM");


            //wait for dependency track to process the SBOM
            await new Promise(resolve => setTimeout(resolve, 2000)); //2 seconds delay

            //fetch vulnerabilities from dependency track
            const vulnerabilitiesResponse = await fetch(`${CONFIG.BASE_URL}/track/getVulnerabilities?projectUuid=${CONFIG.projectUuid}`, {
                headers: {
                    "Authorization": `Bearer ${token}`, 
                    "Cache-Control": "no-cache, no-store, must-revalidate",
                    "Pragma": "no-cache",
                    "Expires": "0"
                }
            });
            if (!vulnerabilitiesResponse.ok) throw new Error("Failed to get vulnerabilities");

            //display the vulnerabilities
            const vulnerabilities = await vulnerabilitiesResponse.json();
            await displayVulnerabilities(vulnerabilities);

            loadingSpinner.style.display = "none";
            generateFeedbackBtn.disabled = false;
        } catch (error) {
            loadingSpinner.style.display = "none";
            vulnerabilitiesList.innerHTML = `<p class="error-message">An error occurred: <strong>${error.message}</strong></p>`;
        }
    });

    //generate feedback using ai
    generateFeedbackBtn.addEventListener("click", async () => {
        feedbackSpinner.style.display = "block";
        generateFeedbackBtn.disabled = true;
        try {
            const token = getToken();
            const response = await fetch(`${CONFIG.BASE_URL}/ai/analyzeVulnerabilities?filePath=${CONFIG.vulnerabilitiesFilePath}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(CONFIG.vulnerabilitiesFilePath)
            });

            if (!response.ok) throw new Error("Failed to analyze vulnerabilities");

            const aiResponse = await response.text();
            aiFeedback.innerText = aiResponse;
            aiFeedback.style.display = "block";
        } catch (error) {
            aiFeedback.style.display = "none";
            aiFeedback.innerHTML = `<p class="error-message">An error occurred: <strong>${error.message}</strong></p>`;
        } finally {
            feedbackSpinner.style.display = "none";
            generateFeedbackBtn.disabled = false;
        }
    });

    //display dependency track dashboard
    displayDashboardBtn.addEventListener("click", () => {
        window.open("http://localhost:8080/dashboard", "_blank");
    });

    document.getElementById("logoutBtn").addEventListener("click", function () {
        localStorage.removeItem("token");
        window.location.href = "signin.html";
    });


    async function displayVulnerabilities(vulnerabilities) {
        console.log("display called");
        console.log(vulnerabilities);
        const vulnerabilitiesList = document.getElementById("vulnerabilitiesList");
        vulnerabilitiesList.innerHTML = "";

        if (!vulnerabilities || vulnerabilities.length === 0) {
            vulnerabilitiesList.innerHTML = "<p>No vulnerabilities found.</p>";
            return;
        }

        //total vulnerabilities 
        const totalVulnerabilities = vulnerabilities.length;
        const countHeader = document.createElement("h3");
        countHeader.innerHTML = `Total Vulnerabilities: ${totalVulnerabilities}`;
        vulnerabilitiesList.appendChild(countHeader);

        //create table to display vulnerabilities
        const table = document.createElement("table");
        table.classList.add("vulnerabilities-table");

        //table header
        const thead = document.createElement("thead");
        thead.innerHTML = `
            <tr>
                <th>Component</th>
                <th>Vulnerability ID</th>
                <th>Severity</th>
                <th>Description</th>
            </tr>
        `;
        table.appendChild(thead);

        //table body
        const tbody = document.createElement("tbody");
        vulnerabilities.forEach((vuln) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${vuln.component.name} (${vuln.component.version})</td>
                <td>${vuln.vulnerability.vulnId}</td>
                <td>${vuln.vulnerability.severity}</td>
                <td>${vuln.vulnerability.description}</td>
            `;
            tbody.appendChild(row);
        });

        table.appendChild(tbody);
        vulnerabilitiesList.appendChild(table);
    }
});
