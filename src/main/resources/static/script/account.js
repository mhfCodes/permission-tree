(function() {

    const accountForm = document.querySelector(".account-form");
    const updateAccountBtn = document.querySelector(".btn-account-update");
    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");
    const navSignup = document.querySelector(".nav-signup");
    const dialogBox = document.querySelector(".dialog-box");
    const dialogContent = document.querySelector(".dialog-content");

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

    let userId;
    let currentUsername = "";
    let jwtToken = "";

    const init = async () => {
        const jwt = localStorage.getItem("jwt");

        if (jwt != null && jwt != "undefined") {
            jwtToken = "Bearer " + localStorage.getItem("jwt");
            
            navSignup.classList.add("hidden");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");
            
            const userData = await getUserData();
            currentUsername = userData.username;
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

    const checkOldPassword = async (oldPassword) => {

        const userObj = {
            userId: userId,
            password: oldPassword
        }

        const response = await fetch("http://localhost:8080/api/account/checkOldPassword", {
            method: 'POST',
            body: JSON.stringify(userObj),
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });

        const data = await response.text();

        return data == "true";
    }

    const validatePassword = async (oldPassword, newPassword, passwordConfirmation) => {

        let checkOldPasswordResult = await checkOldPassword(oldPassword);

        if (!checkOldPasswordResult) {
            return "Old Password Is Not Correct";
        }

        if (newPassword.trim().length === 0) {
            return "Password Can Not Be Empty";
        }

        if (newPassword !== passwordConfirmation) {
            return "Password Confirmation Is Different From Password";
        }

        if (newPassword.length < 8) {
            return "Password Must Be At Least 8 Characters Long";
        }

        if (newPassword.length > 16) {
            return "Password Length Cannot Be More Than 16 Characters";
        }

        if (!/(?=.*?[A-Z])/.test(newPassword)) {
            return "Password Must Contain At Least One Upper Case English Letter";
        }

        if (!/(?=.*?[a-z])/.test(newPassword)) {
            return "Password Must Contain At Least One Lower Case English Letter";
        }

        if (!/(?=.*?[0-9])/.test(newPassword)) {
            return "Password Must Contain At Least One Number";
        }

        if (!/(?=.*?[#?!@$%^&*-])/.test(newPassword)) {
            return "Password Must Contain At Least One Special Character";
        }

        return null;
    }

    const updateAccount = async (e) => {
        e.preventDefault();

        if (usernameInput.value.trim().length === 0) {
            dialogContent.textContent = "Username Can Not Be Empty";
            chooseDialog("error");
            fadeIn();
            return;
        }

        let oldPasswordValue = oldPasswordInput.value;
        let newPasswordValue = newPasswordInput.value;
        let confirmNewPasswordValue = confirmPasswordInput.value;

        if (oldPasswordValue.length == 0 && (newPasswordValue.length > 0 || confirmNewPasswordValue.length > 0)) {
            dialogContent.textContent = "Old Password Can Not Be Empty";
            chooseDialog("error");
            fadeIn();
            return;
        }

        let passwordValidationResult = (oldPasswordValue.length > 0) ? await validatePassword(oldPasswordValue, newPasswordValue, confirmNewPasswordValue) : "empty";

        if(passwordValidationResult != null && passwordValidationResult != "empty") {
            dialogContent.textContent = passwordValidationResult;
            chooseDialog("error");
            fadeIn();
            return;
        }

        const userObj = {
            userId: userId,
            username: usernameInput.value,
            password: (passwordValidationResult == "empty") ? null : newPasswordValue,
            userFirstName: firstNameInput.value,
            userLastName: lastNameInput.value
        }

        const response = await fetch("http://localhost:8080/api/account", {
            method: 'POST',
            body: JSON.stringify(userObj),
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });

        const data = await response.json();
        
        if (data == 0) {

            dialogContent.textContent = "Username Exists";
            chooseDialog("error");
            fadeIn();
        } else if (data > 0 && currentUsername.localeCompare(userObj.username) !== 0) {

            dialogContent.textContent = "Account Updated Successfully (Login Again Please)";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(logout, 3000);
        } else if (data > 0) {

            dialogContent.textContent = "Account Updated Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
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

        userId = data.userId;
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

    const fadeIn = () => {
        let opacity = 0;
        dialogBox.style.display = "block";
        let fadeInInterval = setInterval(() => {
            if (opacity < 1) {
                opacity += 0.1;
                dialogBox.style.opacity = opacity;
            } else {
                clearInterval(fadeInInterval);
                setTimeout(fadeOut, 2000);
            }
        }, 50);
    }

    const fadeOut = () => {
        let opacity = parseFloat(dialogBox.style.opacity);
        let fadeOutInterval = setInterval(() => {
            if (opacity > 0) {
                opacity -= 0.1;
                dialogBox.style.opacity = opacity;    
            } else {
                clearInterval(fadeOutInterval);
                dialogBox.style.display = "none";
            }
        }, 50);
    }

    const chooseDialog = (dialogType) => {
        if (dialogType === "info") {
            dialogBox.classList.add("info");
            dialogBox.classList.remove("error");
            dialogBox.classList.remove("warning");
        } else if (dialogType === "warning") {
            dialogBox.classList.add("warning");
            dialogBox.classList.remove("info");
            dialogBox.classList.remove("error");
        } else if (dialogType === "error") {
            dialogBox.classList.add("error");
            dialogBox.classList.remove("info");
            dialogBox.classList.remove("warning");
        }
    }

    document.addEventListener('DOMContentLoaded', init);
    dropdownBtn.addEventListener('click', dropdownContentToggle);
    updateAccountBtn.addEventListener('click', updateAccount);
    logoutBtn.addEventListener('click', logout);

})();