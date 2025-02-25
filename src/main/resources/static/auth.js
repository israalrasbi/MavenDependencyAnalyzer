document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signupForm");
    const signinForm = document.getElementById("signinForm");

    //signup
    if (signupForm) {
        signupForm.addEventListener("submit", async (event) => {
            event.preventDefault();
            const userName = document.getElementById("userName").value;
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            
            try {
                const response = await fetch(`${CONFIG.BASE_URL}/auth/signup`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ userName, email, password, role: "USER" })
                });

                const result = await response.json();
                document.getElementById("signupMessage").textContent = result.message || result.error;

                if (response.ok) {
                    window.location.href = "signin.html";
                }
            } catch (error) {
                document.getElementById("signupMessage").textContent = "An error occurred.";
            }
        });
    }

    //signin
    if (signinForm) {
        signinForm.addEventListener("submit", async (event) => {
            event.preventDefault();
            const userName = document.getElementById("userNameLogin").value;
            const password = document.getElementById("passwordLogin").value;

            try {
                const response = await fetch(`${CONFIG.BASE_URL}/auth/signin`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ userName, password })
                });

                const result = await response.json();
                document.getElementById("signinMessage").textContent = result.token ? "Login successful!" : result.error;

                if (response.ok) {
                    localStorage.setItem("token", result.token);
                    window.location.href = "index.html";
                }
            } catch (error) {
                document.getElementById("signinMessage").textContent = "An error occurred.";
            }
        });
    }
});
