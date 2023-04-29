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
    const navSignup = document.querySelector(".nav-signup");
    const noAccount = document.querySelector(".no-account");

    const dropdownBtn = document.querySelector(".btn-dropdown");
    const dropdownContent = document.querySelector(".dropdown-content");
    const arrowUp = document.getElementById("arrow-up");
    const arrowDown = document.getElementById("arrow-down");

    const hasPermissionElements = document.querySelectorAll("[data-has-permission]");
    const hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
    let permissions = [];

    const init = async () => {
        const jwt = localStorage.getItem("jwt");

        if (jwt != null && jwt != "undefined") {
            
            navSignup.classList.add("hidden");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");
            loggedinText.classList.remove("hidden");
            nameContainer.classList.remove("hidden");
            loginForm.classList.add("hidden");
            noAccount.classList.add("hidden");
            
            permissions = await getUserPermissions();
            auditPermissions();

            const userData = await getUserData();
            name.textContent = userData.userFirstName + " " + userData.userLastName;
        } else {
            
            navSignup.classList.remove("hidden");
            navLogin.classList.remove("hidden");
            navLogout.classList.add("hidden");
            loggedinText.classList.add("hidden");
            nameContainer.classList.add("hidden");
            loginForm.classList.remove("hidden");
            noAccount.classList.remove("hidden");

            hasPermissionElements.forEach(element => element.remove());
            hasAnyPermissionsElements.forEach(element => element.remove());

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

    const getUserPermissions = async () => {
        const response = await fetch("http://localhost:8080/api/account", {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("jwt")
            }
        });
        const data = await response.json();
        if (data) {
            return data.permissionIds;
        }
    }

    const auditPermissions = () => {
        hasPermissionElements.forEach(element => {
            const elementPermission = element.dataset.hasPermission;
            const permissionExists = permissions.includes(elementPermission);
            if (permissionExists) {
                element.classList.remove("hidden");
            } else {
                element.remove();
            }
        });
        hasAnyPermissionsElements.forEach(element => {
            const elementPermissions = element.dataset.hasAnyPermission;
            elementPermissions.split(",").forEach(elementPermission => {
                if (permissions.includes(elementPermission)) {
                    element.classList.remove("hidden");
                }
            });
            if (element.classList.contains("hidden")) {
                element.remove();
            }
        });
    }

    const dropdownContentToggle = () => {
        dropdownContent.classList.toggle("hidden");
        arrowUp.classList.toggle("hidden");
        arrowDown.classList.toggle("hidden");
    }

    document.addEventListener('DOMContentLoaded', init);
    dropdownBtn.addEventListener('click', dropdownContentToggle);
    loginBtn.addEventListener('click', login);
    logoutBtn.addEventListener('click', logout);

})();