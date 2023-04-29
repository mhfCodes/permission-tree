(function() {

    const signupForm = document.querySelector(".signup-form");
    const loggedinText =  document.querySelector(".loggedin-text");
    const usernameExists = document.querySelector(".username-exists");
    const firstNameInput = document.getElementById("firstName");
    const lastNameInput = document.getElementById("lastName");
    const usernameInput = document.getElementById("username");
    const passwordInput = document.getElementById("password");
    const nameContainer = document.querySelector(".name-container");
    const name = document.querySelector(".name");
    const signupBtn = document.querySelector(".btn-signup");
    const navSignup = document.querySelector(".nav-signup");
    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");
    const haveAccount = document.querySelector(".have-account");

    const dropdownBtn = document.querySelector(".btn-dropdown");
    const dropdownContent = document.querySelector(".dropdown-content");
    const arrowUp = document.getElementById("arrow-up");
    const arrowDown = document.getElementById("arrow-down");

    const hasPermissionElements = document.querySelectorAll("[data-has-permission]");
    const hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
    let permissions = [];

    let jwtToken = "";

    const init = async () => {
        jwtToken = localStorage.getItem("jwt");

        if (jwtToken != null && jwtToken != "undefined") {
            navSignup.classList.add("hidden");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");
            signupForm.classList.add("hidden");
            loggedinText.classList.remove("hidden");
            nameContainer.classList.remove("hidden");
            haveAccount.classList.add("hidden");

            const userData = await getUserData();
            permissions = userData.permissionIds;
            auditPermissions();
            name.textContent = userData.userFirstName + " " + userData.userLastName;

        } else {
            navSignup.classList.remove("hidden");
            navLogin.classList.remove("hidden");
            navLogout.classList.add("hidden");
            signupForm.classList.remove("hidden");
            loggedinText.classList.add("hidden");
            nameContainer.classList.add("hidden");
            haveAccount.classList.remove("hidden");

            hasPermissionElements.forEach(element => element.remove());
            hasAnyPermissionsElements.forEach(element => element.remove());

            name.textContent = "";
        }

    }

    const signup = async (e) => {
        e.preventDefault();

        const userObj = {
            username: usernameInput.value,
            password: passwordInput.value,
            userFirstName: firstNameInput.value,
            userLastName: lastNameInput.value
        }
        
        const signupResponse = await fetch("http://localhost:8080/signup", {
            method: 'POST',
            body: JSON.stringify(userObj),
            headers: {
                "Content-Type": "application/json"
            }
        })

        if (signupResponse.status == 400) {
            usernameExists.classList.remove("hidden");
            setTimeout(() => {
                usernameExists.classList.add("hidden");
            }, 3000);
            return;
        }

        const data = await signupResponse.json();

        if (data > 0) {

            const authReq = {
                username: usernameInput.value,
                password: passwordInput.value
            }

            const loginResponse = await fetch("http://localhost:8080/login", {
                method: 'POST',
                body: JSON.stringify(authReq),
                headers: {
                    "Content-Type": "application/json"
                }
            });

            const data = await loginResponse.json();

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
                Authorization: "Bearer " + jwtToken
            }
        })
        const data = await response.json();
        return data;
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
    signupBtn.addEventListener('click', signup);
    logoutBtn.addEventListener('click', logout);


})();