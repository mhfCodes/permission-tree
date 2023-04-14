(function() {

    const loggedinText = document.querySelector(".loggedin-text");
    const loginForm = document.querySelector(".login-form");
    const loginBtn = document.querySelector(".btn-login");
    const usernameInput = document.getElementById("username");
    const passwordInput = document.getElementById("password");
    const nameContainer = document.querySelector(".name-container");
    const name = document.querySelector(".name");
    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");
    const invCred = document.querySelector(".invalid-cred");


    const init = async () => {
        const jwt = localStorage.getItem("jwt");

        if (jwt != null && jwt != "undefined") {
            
            loggedinText.classList.remove("hidden");
            nameContainer.classList.remove("hidden");
            navLogout.classList.remove("hidden");
            loginForm.classList.add("hidden");
            navLogin.classList.add("hidden");

            const userData = await getUserData();
            name.textContent = userData.userFirstName + " " + userData.userLastName;
        } else {
            
            loggedinText.classList.add("hidden");
            nameContainer.classList.add("hidden");
            navLogout.classList.add("hidden");
            loginForm.classList.remove("hidden");
            navLogin.classList.remove("hidden");

            name.textContent = "";
        }

    }

    const login = async (e) => {
        e.preventDefault();

        const authReq = {
            username: usernameInput.value,
            password: passwordInput.value
        }

        const response = await fetch("http://localhost:8080/login", {
            method: 'POST',
            body: JSON.stringify(authReq),
            headers: {
                "Content-Type": "application/json"
            }
        });

        const data = await response.json();
        
        if (response.status == 400) {
            invCred.classList.remove("hidden");
            setTimeout(() => {
                invCred.classList.add("hidden");
            }, 3000);
        } else if (data && response.status == 200) {
            localStorage.setItem("jwt", data.jwtToken);
            window.location.reload();
        }
    }

    const logout = () => {
        localStorage.removeItem("jwt");
        window.location.replace("/html/index.html");
    }

    const getUserData = async () => {
        const response = await fetch("http://localhost:8080/api/account", {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("jwt")
            }
        })
        const data = await response.json();
        return data;
    }

    document.addEventListener('DOMContentLoaded', init);
    loginBtn.addEventListener('click', login);
    logoutBtn.addEventListener('click', logout);

})();