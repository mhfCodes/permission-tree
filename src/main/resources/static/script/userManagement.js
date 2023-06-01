(function() {

    const tableBody = document.querySelector(".table-body");
    const addModalRoleTableBody = document.querySelector(".add-modal-role-table-body");
    const addModal = document.querySelector(".add-modal");
    const deleteModal = document.querySelector(".delete-modal");
    const changePasswordModal = document.querySelector(".password-modal");
    const overlay = document.querySelector(".overlay");
    const searchBox = document.querySelector(".search-box");
    const dialogBox = document.querySelector(".dialog-box");
    const dialogContent = document.querySelector(".dialog-content");

    const addBtn = document.querySelector(".btn-add");
    const addModalCloseBtn = document.querySelector(".add-modal-btn-close");
    const deleteModalCloseBtn = document.querySelector(".delete-modal-btn-close");
    const changePasswordModalCloseBtn = document.querySelector(".password-modal-btn-close");
    const saveBtn = document.querySelector(".btn-submit");
    const searchBtn = document.querySelector(".btn-search");
    const findBtn = document.querySelector(".btn-find");
    const yesDelBtn = document.querySelector(".btn-yes");
    const noDelBtn = document.querySelector(".btn-no");
    const updatePasswordBtn = document.querySelector(".btn-password-submit");
    const allRolesSelectorBtn = document.getElementById("allRolesSelector");

    const passwordContainer = document.querySelector(".password");
    const confirmPasswordContainer = document.querySelector(".confirmPassword");

    const usernameInput = document.getElementById("username");
    const firstNameInput = document.getElementById("firstName");
    const lastNameInput = document.getElementById("lastName");

    const searchUsernameInput = document.getElementById("search-username");
    const searchFirstNameInput = document.getElementById("search-firstName");
    const searchLastNameInput = document.getElementById("search-lastName");

    const newPasswordInput = document.getElementById("newPassword");
    const confirmNewPasswordInput = document.getElementById("confirmNewPassword");

    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");

    let currentUser;
    let currentUserId;
    let isEditing = false;
    let jwtToken = "";

    const dropdownBtn = document.querySelector(".btn-dropdown");
    const dropdownContent = document.querySelector(".dropdown-content");
    const arrowUp = document.getElementById("arrow-up");
    const arrowDown = document.getElementById("arrow-down");

    let hasPermissionElements = document.querySelectorAll("[data-has-permission]");
    let hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
    let permissions = [];
    let selectedRoles = [];

    const init = async () => {

        if (localStorage.getItem("jwt") != null && localStorage.getItem("jwt") != "undefined") {
            jwtToken = "Bearer " + localStorage.getItem("jwt");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");

            permissions = await getUserPermissions();

            if (permissions.includes("ROLE_10")) {
                await fetchUsers();
            }

            hasPermissionElements = document.querySelectorAll("[data-has-permission]");
            hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");

            auditPermissions();
        } else {
            hasPermissionElements = document.querySelectorAll("[data-has-permission]");
            hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");

            hasPermissionElements.forEach(element => element.remove());
            hasAnyPermissionsElements.forEach(element => element.remove());
            window.location.replace("/html/login.html");
        }

    }

    const fetchUsers = async () => {
        const response = await fetch("http://localhost:8080/api/user", {
            headers: {
                Authorization: jwtToken
            }
        });
        const users = await response.json();

        users.forEach(user => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${user.username}</td><td>${user.userFirstName != null ? user.userFirstName : '-'}</td><td>${user.userLastName != null ? user.userLastName : '-'}</td><td class="hidden" data-has-any-permission="ROLE_11,ROLE_12"><button class="btn btn-small btn-edit hidden" data-userId="${user.userId}" data-has-permission="ROLE_11">Edit</button><button class="btn btn-small btn-delete hidden" data-userId="${user.userId}" data-has-permission="ROLE_12">Delete</button><button class="btn btn-small btn-change-password hidden" data-userId="${user.userId}" data-has-permission="ROLE_11">Change Password</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();
        addChangePasswordClickEventListener();
    }

    const fetchRoles = async (tableBody) => {
        tableBody.innerHTML = "";

        const response = await fetch("http://localhost:8080/api/role", {
            headers: {
                Authorization: jwtToken
            }
        });
        const roles = await response.json();

        roles.forEach(role => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td class="role"><input type="checkbox" data-roleId="${role.roleId}"/></td><td>${role.roleName}</td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });

    }

    const fillRolesList = (tableBody) => {
        selectedRoles = [];

        const roles = tableBody.querySelectorAll(".role");

        roles.forEach(role => {
            let checkbox = role.firstElementChild;
            if (checkbox.checked) {
                let roleObj = {
                    id: parseInt(checkbox.dataset.roleid),
                    name: role.nextElementSibling.textContent
                }
                selectedRoles.push(roleObj);
            }
        })

        console.log(selectedRoles);
    }

    const validatePassword = (password, passwordConfirmation) => {

        if (password.trim().length === 0) {
            return "Password Can Not Be Empty";
        }

        if (password !== passwordConfirmation) {
            return "Password Confirmation Is Different From Password";
        }

        if (password.length < 8) {
            return "Password Must Be At Least 8 Characters Long";
        }

        if (password.length > 16) {
            return "Password Length Cannot Be More Than 16 Characters";
        }

        if (!/(?=.*?[A-Z])/.test(password)) {
            return "Password Must Contain At Least One Upper Case English Letter";
        }

        if (!/(?=.*?[a-z])/.test(password)) {
            return "Password Must Contain At Least One Lower Case English Letter";
        }

        if (!/(?=.*?[0-9])/.test(password)) {
            return "Password Must Contain At Least One Number";
        }

        if (!/(?=.*?[#?!@$%^&*-])/.test(password)) {
            return "Password Must Contain At Least One Special Character";
        }

        return null;
    }

    const saveUser = async (e) => {
        e.preventDefault();
        fillRolesList(addModalRoleTableBody);

        if (usernameInput.value.trim().length === 0) {
            dialogContent.textContent = "Username Can Not Be Empty";
            chooseDialog("error");
            fadeIn();
            return;
        }

        let passwordValue = passwordContainer.querySelector("#password").value;
        let passwordConfirmationValue = confirmPasswordContainer.querySelector("#confirmPassword").value;
        let passwordValidationResult = validatePassword(passwordValue, passwordConfirmationValue);

        if(passwordValidationResult != null) {
            dialogContent.textContent = passwordValidationResult;
            chooseDialog("error");
            fadeIn();
            return;
        }


        if (selectedRoles.length === 0) {
            dialogContent.textContent = "You Must At Least Select A Role For This User";
            chooseDialog("error");
            fadeIn();
            return;
        }

        // TODO add role values
        const userObj = {
            id: null,
            username: usernameInput.value,
            password: passwordValue,
            userFirstName: firstNameInput.value,
            userLastName: lastNameInput.value,
            roles: selectedRoles
        }

        const response = await fetch("http://localhost:8080/api/user", {
            body: JSON.stringify(userObj),
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });
        const data = await response.text();
        if (data > 0) {
            dialogContent.textContent = "User Added Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
        } else if (data == 0) {
            dialogContent.textContent = "Username Exists";
            chooseDialog("error");
            fadeIn();
        }

    }

    const editUser = async (e) => {
        e.preventDefault();

        //TODO add role values
        const userObj = {
            id: currentUser.userId,
            username: usernameInput.value,
            userFirstName: firstNameInput.value,
            userLastName: lastNameInput.value
        }

        const response = await fetch("http://localhost:8080/api/user", {
            body: JSON.stringify(userObj),
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });

        const data = await response.text();
        if (data > 0) {
            dialogContent.textContent = "User Edited Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
        }
        
    }

    const delUser = async () => {
        
        const response = await fetch(`http://localhost:8080/api/user/${currentUserId}`, {
            method: 'DELETE',
            headers: {
                Authorization: jwtToken
            }
        })
        const data = await response.text();

        if (data === "true") {
            dialogContent.textContent = "User Deleted Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
        }
    }

    const find = async () => {

        //TODO add role values
        const searchUserObj = {
            id: null,
            username: searchUsernameInput.value,
            userFirstName: searchFirstNameInput.value,
            userLastName: searchLastNameInput.value
        }

        const response = await fetch ("http://localhost:8080/api/user/search", {
            method: 'POST',
            body: JSON.stringify(searchUserObj),
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });
        const data = await response.json();

        tableBody.innerHTML = "";
        data.forEach(user => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${user.username}</td><td>${user.userFirstName != null ? user.userFirstName : '-'}</td><td>${user.userLastName != null ? user.userLastName : '-'}</td><td class="hidden" data-has-any-permission="ROLE_11,ROLE_12"><button class="btn btn-small btn-edit hidden" data-userId="${user.userId}" data-has-permission="ROLE_11">Edit</button><button class="btn btn-small btn-delete hidden" data-userId="${user.userId}" data-has-permission="ROLE_12">Delete</button><button class="btn btn-small btn-change-password hidden" data-userId="${user.userId}" data-has-permission="ROLE_11">Change Password</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();
        addChangePasswordClickEventListener();        

        hasPermissionElements = document.querySelectorAll("[data-has-permission]");
        hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
        auditPermissions();
    };

    const loadUser = async (e) => {

        passwordContainer.style.display = "none";
        confirmPasswordContainer.style.display = "none";

        const response = await fetch(`http://localhost:8080/api/user/${e.target.dataset.userid}`, {
            headers: {
                Authorization: jwtToken
            }
        });
        currentUser = await response.json();
        
        addModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Edit";
        usernameInput.value = currentUser.username;
        firstNameInput.value = currentUser.userFirstName;
        lastNameInput.value = currentUser.userLastName;
        isEditing = true;

    }

    const addEditClickEventListener = () => {
        const editBtns = document.querySelectorAll(".btn-edit");
        editBtns.forEach(editBtn => {
            editBtn.addEventListener('click', (e) => {
                if (permissions.includes("ROLE_10")) {
                    loadUser(e);
                }
            });
        })
    }

    const addDeleteClickEventListener = () => {
        const deleteBtns = document.querySelectorAll(".btn-delete");
        deleteBtns.forEach(deleteBtn => {
            deleteBtn.addEventListener('click', (e) => {
                deleteModal.classList.remove("hidden");
                overlay.classList.remove("hidden");
                currentUserId = e.target.dataset.userid;
            });
        })
    }

    const addChangePasswordClickEventListener = () => {
        const changePasswordBtns = document.querySelectorAll(".btn-change-password");
        changePasswordBtns.forEach(changePasswordBtn => {
            changePasswordBtn.addEventListener('click', (e) => {
                changePasswordModal.classList.remove("hidden");
                overlay.classList.remove("hidden");
                newPasswordInput.value = "";
                confirmNewPasswordInput.value = "";
                currentUserId = e.target.dataset.userid;
            })
        })
    }

    const logout = () => {
        localStorage.removeItem("jwt");
        window.location.replace("/html/index.html");
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
    saveBtn.addEventListener('click', (e) => {
        if (isEditing && permissions.includes("ROLE_11")) {
            editUser(e);
        } else if (!isEditing && permissions.includes("ROLE_9")) {
            saveUser(e);
        }
    });
    dropdownBtn.addEventListener('click', dropdownContentToggle);
    findBtn.addEventListener('click', () => {
        if (permissions.includes("ROLE_10")) {
            find();
        }
    });
    yesDelBtn.addEventListener('click', () => {
        if (permissions.includes("ROLE_12")) {
            delUser();
        }
    });
    logoutBtn.addEventListener('click', logout);

    addBtn.addEventListener('click', async () => {

        passwordContainer.style.display = "block";
        confirmPasswordContainer.style.display = "block";
        
        if (permissions.includes("ROLE_15")) {
           await fetchRoles(addModalRoleTableBody);
        }

        addModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Save";
        usernameInput.value = "";
        firstNameInput.value = "";
        lastNameInput.value = "";
        isEditing = false;

    });
    addModalCloseBtn.addEventListener('click', () => {
        addModal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    changePasswordModalCloseBtn.addEventListener('click', () => {
        changePasswordModal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    deleteModalCloseBtn.addEventListener('click', () => {
        deleteModal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    noDelBtn.addEventListener('click', () => {
        deleteModal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    searchBtn.addEventListener('click', () => {
        searchBox.classList.toggle("hidden");
    });
    allRolesSelectorBtn.addEventListener('click', () => {

        const roles = document.querySelectorAll(".role");
        roles.forEach(role => {
            let roleCheckBox = role.firstElementChild;
            roleCheckBox.checked = allRolesSelectorBtn.checked;
        });
    })
})();