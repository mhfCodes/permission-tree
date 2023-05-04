(function() {

    const tableBody = document.querySelector(".table-body");
    const addModal = document.querySelector(".add-modal");
    const deleteModal = document.querySelector(".delete-modal");
    const overlay = document.querySelector(".overlay");
    const searchBox = document.querySelector(".search-box");

    const addBtn = document.querySelector(".btn-add");
    const addModalCloseBtn = document.querySelector(".add-modal-btn-close");
    const deleteModalCloseBtn = document.querySelector(".delete-modal-btn-close");
    const saveBtn = document.querySelector(".btn-submit");
    const searchBtn = document.querySelector(".btn-search");
    const findBtn = document.querySelector(".btn-find");
    const yesDelBtn = document.querySelector(".btn-yes");
    const noDelBtn = document.querySelector(".btn-no");

    const nameInput = document.getElementById("name");

    const searchNameInput = document.getElementById("search-name");

    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");

    let currentRole;
    let currentRoleId;
    let isEditing = false;
    let jwtToken = "";

    const dropdownBtn = document.querySelector(".btn-dropdown");
    const dropdownContent = document.querySelector(".dropdown-content");
    const arrowUp = document.getElementById("arrow-up");
    const arrowDown = document.getElementById("arrow-down");

    let hasPermissionElements = document.querySelectorAll("[data-has-permission]");
    let hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
    let permissions = [];

    const init = async () => {

        if (localStorage.getItem("jwt") != null && localStorage.getItem("jwt") != "undefined") {
            jwtToken = "Bearer " + localStorage.getItem("jwt");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");

            permissions = await getUserPermissions();

            if (permissions.includes("ROLE_15")) {
                await fetchRoles();
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

    const fetchRoles = async () => {
        const response = await fetch("http://localhost:8080/api/role", {
            headers: {
                Authorization: jwtToken
            }
        });
        const roles = await response.json();

        roles.forEach(role => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${role.roleId}</td><td>${role.roleName}</td><td class="hidden" data-has-any-permission="ROLE_16,ROLE_17"><button class="btn btn-small btn-edit hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_16">Edit</button><button class="btn btn-small btn-delete hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_17">Delete</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();
    }

    const saveRole = async (e) => {
        e.preventDefault();

        const roleObj = {
            id: null,
            name: nameInput.value
        }
        const response = await fetch("http://localhost:8080/api/role", {
            body: JSON.stringify(roleObj),
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });
        const data = await response.text();
        if (data > 0) {
            window.location.reload();
        }
    }

    const editRole = async (e) => {
        e.preventDefault();

        const roleObj = {
            id: currentRole.roleId,
            name: nameInput.value 
        }

        const response = await fetch("http://localhost:8080/api/role", {
            body: JSON.stringify(roleObj),
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });

        const data = await response.text();
        if (data > 0) {
            window.location.reload();
        }
        
    }

    const delRole = async () => {
        
        const response = await fetch(`http://localhost:8080/api/role/${currentRoleId}`, {
            method: 'DELETE',
            headers: {
                Authorization: jwtToken
            }
        })
        const data = await response.text();

        if (data === "true") {
            window.location.reload();
        }
    }

    const find = async () => {

        const searchRoleObj = {
            id: null,
            name: searchNameInput.value
        };

        const response = await fetch ("http://localhost:8080/api/role/search", {
            method: 'POST',
            body: JSON.stringify(searchRoleObj),
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });
        const data = await response.json();

        tableBody.innerHTML = "";
        data.forEach(role => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${role.roleId}</td><td>${role.roleName}</td><td class="hidden" data-has-any-permission="ROLE_16,ROLE_17"><button class="btn btn-small btn-edit hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_16">Edit</button><button class="btn btn-small btn-delete hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_17">Delete</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();

        hasPermissionElements = document.querySelectorAll("[data-has-permission]");
        hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
        auditPermissions();
    };

    const loadRole = async (e) => {
        const response = await fetch(`http://localhost:8080/api/role/${e.target.dataset.roleid}`, {
            headers: {
                Authorization: jwtToken
            }
        });
        currentRole = await response.json();
        
        addModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Edit";
        nameInput.value = currentRole.roleName;
        isEditing = true;

    }

    const addEditClickEventListener = () => {
        const editBtns = document.querySelectorAll(".btn-edit");
        editBtns.forEach(editBtn => {
            editBtn.addEventListener('click', (e) => {
                if (permissions.includes("ROLE_15")) {
                    loadRole(e);
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
                currentRoleId = e.target.dataset.roleid;
            });
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

    document.addEventListener('DOMContentLoaded', init);
    saveBtn.addEventListener('click', (e) => {
        if (isEditing && permissions.includes("ROLE_16")) {
            editRole(e);
        } else if (!isEditing && permissions.includes("ROLE_14")) {
            saveRole(e);
        }
    });
    dropdownBtn.addEventListener('click', dropdownContentToggle);
    findBtn.addEventListener('click', () => {
        if (permissions.includes("ROLE_15")) {
            find();
        }
    });
    yesDelBtn.addEventListener('click', () => {
        if (permissions.includes("ROLE_17")) {
            delRole();
        }
    });
    logoutBtn.addEventListener('click', logout);

    addBtn.addEventListener('click', () => {
        addModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Save";
        nameInput.value = "";
        isEditing = false;
    });
    addModalCloseBtn.addEventListener('click', () => {
        addModal.classList.add("hidden");
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
})();