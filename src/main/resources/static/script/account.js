(function() {

    const accountForm = document.querySelector(".account-form");
    const updateAccountBtn = document.querySelector(".btn-account-update");
    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");
    const navSignup = document.querySelector(".nav-signup");

    const usernameInput = document.getElementById("username");
    const firstNameInput = document.getElementById("firstName");
    const lastNameInput = document.getElementById("lastName");
    const oldPasswordInput = document.getElementById("oldPassword");
    const newPasswordInput = document.getElementById("newPassword");
    const confirmPasswordInput = document.getElementById("confirmPassword");

    const dropdownBtn = document.querySelector(".btn-dropdown");
    const dropdownContent = document.querySelector(".dropdown-content");
    const arrowUp = document.getElementById("arrow-up");
    const arrowDown = document.getElementById("arrow-down");

    const hasPermissionElements = document.querySelectorAll("[data-has-permission]");
    const hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
    let permissions = [];

    let jwtToken = "";

    const init = async () => {
        const jwt = localStorage.getItem("jwt");

        if (jwt != null && jwt != "undefined") {
            jwtToken = "Bearer " + localStorage.getItem("jwt");
            
            navSignup.classList.add("hidden");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");
            
            const userData = await getUserData();
            permissions = userData.permissionIds;
            auditPermissions();
        } else {
            
            navSignup.classList.remove("hidden");
            navLogin.classList.remove("hidden");
            navLogout.classList.add("hidden");

            hasPermissionElements.forEach(element => element.remove());
            hasAnyPermissionsElements.forEach(element => element.remove());
        }

    }

    const updateAccount = async (e) => {
        // e.preventDefault();

        // const authReq = {
        //     username: usernameInput.value,
        //     password: passwordInput.value
        // }

        // const response = await fetch("http://localhost:8080/login", {
        //     method: 'POST',
        //     body: JSON.stringify(authReq),
        //     headers: {
        //         "Content-Type": "application/json"
        //     }
        // });

        // const data = await response.json();
        
        // if (response.status == 400) {
        //     invCred.classList.remove("hidden");
        //     setTimeout(() => {
        //         invCred.classList.add("hidden");
        //     }, 3000);
        // } else if (data && response.status == 200) {
        //     localStorage.setItem("jwt", data.jwtToken);
        //     window.location.reload();
        // }
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

        usernameInput.value = data.username;
        firstNameInput.value = data.userFirstName;
        lastNameInput.value = data.userLastName;

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
    updateAccountBtn.addEventListener('click', updateAccount);
    logoutBtn.addEventListener('click', logout);

})();