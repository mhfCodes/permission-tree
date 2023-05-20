(function() {

    const tableBody = document.querySelector(".table-body");
    const addModal = document.querySelector(".add-modal");
    const deleteModal = document.querySelector(".delete-modal");
    const permissionModal = document.querySelector(".permission-modal");
    const overlay = document.querySelector(".overlay");
    const searchBox = document.querySelector(".search-box");
    const dialogBox = document.querySelector(".dialog-box");
    const dialogContent = document.querySelector(".dialog-content");
    const permissionWrapper = document.querySelector(".permission-wrapper");

    const addBtn = document.querySelector(".btn-add");
    const addModalCloseBtn = document.querySelector(".add-modal-btn-close");
    const deleteModalCloseBtn = document.querySelector(".delete-modal-btn-close");
    const permissionModalCloseBtn = document.querySelector(".permission-modal-btn-close");
    const saveBtn = document.querySelector(".btn-submit");
    const searchBtn = document.querySelector(".btn-search");
    const findBtn = document.querySelector(".btn-find");
    const yesDelBtn = document.querySelector(".btn-yes");
    const noDelBtn = document.querySelector(".btn-no");
    const permissionUpdateBtn = document.querySelector(".btn-permission-submit");

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
            tr.innerHTML = `<td>${role.roleId}</td><td>${role.roleName}</td><td class="hidden" data-has-any-permission="ROLE_16,ROLE_17"><button class="btn btn-small btn-edit hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_16">Edit</button><button class="btn btn-small btn-delete hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_17">Delete</button><button class="btn btn-small btn-permission hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_41">Permissions</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();
        addPermissionClickEventListener();
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
            dialogContent.textContent = "Role Added Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
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
            dialogContent.textContent = "Role Edited Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
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

        if (response.status == 400) {
            dialogContent.textContent = "A User Is Connected To This Role.";
            chooseDialog("error");
            fadeIn();
        } else if (data === "true") {
            dialogContent.textContent = "Role Deleted Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
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
            tr.innerHTML = `<td>${role.roleId}</td><td>${role.roleName}</td><td class="hidden" data-has-any-permission="ROLE_16,ROLE_17"><button class="btn btn-small btn-edit hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_16">Edit</button><button class="btn btn-small btn-delete hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_17">Delete</button><button class="btn btn-small btn-permission hidden" data-roleId="${role.roleId}" data-has-permission="ROLE_41">Permissions</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();
        addPermissionClickEventListener();
        

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

    const updatePermissions = async (e) => {
        e.preventDefault();

        let permissionIds = "";

        const permissionCheckBoxes = document.querySelectorAll(".permission-checkbox");
        permissionCheckBoxes.forEach(permissionCheckBox => {

            if (permissionCheckBox.checked) {
                permissionIds += permissionCheckBox.dataset.permissionId + ",";
            }
        });
        permissionIds = permissionIds.slice(0, -1);

        if (permissionIds.length === 0) {
            dialogContent.textContent = "No Permission Has Been Chosen";
            chooseDialog("error");
            fadeIn();
            return;
        }

        const rolePermissionObj = {
            roleId: parseInt(currentRoleId),
            rolePermissionIds: permissionIds
        }

        const response = await fetch (`http://localhost:8080/api/role/permission`, {
            method: 'POST',
            body: JSON.stringify(rolePermissionObj),
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });

        const data = await response.json();

        if (data > 0) {
            dialogContent.textContent = "Role's Permissions Updated Successfully";
            chooseDialog("info");
            fadeIn();

            setTimeout(() => window.location.reload(), 3000);
        } else {
            dialogContent.textContent = "Role's Permissions Update Operation Was Not Successful";
            chooseDialog("error");
            fadeIn();
        }
    }

    const loadRolePermissions = async (e) => {
        const response = await fetch(`http://localhost:8080/api/role/permissionTree/${currentRoleId}`, {
            headers: {
                Authorization: jwtToken
            }
        });

        const data = await response.json();

        makePermissionTree(data);
    }

    const makePermissionTree = (permissions) => {

        const permissionContainer = document.createElement("ul");
        permissionContainer.classList.add("permission-container");
        permissionContainer.innerHTML = ``;

        permissions.forEach(rootPermission => {
            let hasChild = rootPermission.permissionChildren.length > 0;
            let rootPermissionHTML = `<li class="${hasChild ? 'parentNode' : 'childNode'}"><input type="checkbox" class="permission-checkbox" ${rootPermission.permissionSelected ? "checked" : ""} data-permission-id="${rootPermission.permissionId}"/>${hasChild ? '<span class="permission-toggler"><i class="bx bxs-right-arrow"></i></span>' : '<i class="bx bx-wifi-0"></i>'}${rootPermission.permissionName}</li>`;

            if (rootPermission.permissionChildren.length > 0) {
                let childOfRootPermissionHTML = makeChildNodes(rootPermission.permissionChildren);
                rootPermissionHTML += childOfRootPermissionHTML;
            }

            permissionContainer.innerHTML += rootPermissionHTML;
        });

        permissionWrapper.insertAdjacentElement("beforeend", permissionContainer);
        addPermissionTogglerClickEventListener();
        addPermissionCheckBoxClickEventListener();
    }

    const makeChildNodes = (children) => {

        let childrenHTML = `<li class="childNode hidden"><ul>`;

        children.forEach(child => {
            let hasChild = child.permissionChildren.length > 0;
            let childPermissionHTML = `<li class="${hasChild ? 'parentNode' : 'childNode'}"><input type="checkbox" class="permission-checkbox" ${child.permissionSelected ? "checked" : ""} data-permission-id="${child.permissionId}"/>${hasChild ? '<span class="permission-toggler"><i class="bx bxs-right-arrow"></i></span>' : '<i class="bx bx-wifi-0"></i>'}${child.permissionName}</li>`;

            if (child.permissionChildren.length > 0) {
                let childOfChildPermissionHTML = makeChildNodes(child.permissionChildren);
                childPermissionHTML += childOfChildPermissionHTML;
            }

            childrenHTML += childPermissionHTML;
        });
        
        childrenHTML += `</ul></li>`;
        return childrenHTML;
    }

    const addPermissionTogglerClickEventListener = () => {
        const permissionTogglers = document.querySelectorAll(".permission-toggler");
        permissionTogglers.forEach(permissionToggler => {
            permissionToggler.addEventListener('click', () => {

                const arrow = permissionToggler.firstChild;
                if (arrow.classList.contains("bxs-right-arrow")) {
                    arrow.classList.remove("bxs-right-arrow");
                    arrow.classList.add("bxs-down-arrow");
                } else if (arrow.classList.contains("bxs-down-arrow")) {
                    arrow.classList.remove("bxs-down-arrow");
                    arrow.classList.add("bxs-right-arrow");
                }

                const parentLi = permissionToggler.parentElement;
                const childLi = parentLi.nextElementSibling;
                childLi.classList.toggle("hidden");
            })
        })
    }

    const addPermissionCheckBoxClickEventListener = () => {
        
        const permissionCheckBoxes = document.querySelectorAll(".permission-checkbox");

        permissionCheckBoxes.forEach(permissionCheckBox => {

            permissionCheckBox.addEventListener('click', () => {

                const parentLi = permissionCheckBox.parentElement;
                const childLi = parentLi.nextElementSibling;
                let parentUl = parentLi.parentElement;

                // downward hierarchical click event listener
                if (parentLi.classList.contains("parentNode")) {
                    const childCheckBoxes = childLi.querySelectorAll(".permission-checkbox");
                    childCheckBoxes.forEach(childCheckBox => {
                        childCheckBox.checked = permissionCheckBox.checked;
                    })
                }

                // upward hierarchical click event listener
                let allDescendantPermissionAreChecked = validatePermissionChecked(parentUl);

                let allParentPermissionDescendantPermissionAreChecked = true;

                while (!parentUl.classList.contains("permission-container")) {
                    const parentPermissionCheckBox = parentUl.parentElement.previousElementSibling.querySelector(".permission-checkbox");
                    if (allDescendantPermissionAreChecked && allParentPermissionDescendantPermissionAreChecked) {
                        parentPermissionCheckBox.checked = true;
                    } else {
                        parentPermissionCheckBox.checked = false;
                    }
                    parentUl = parentUl.parentElement.parentElement;
                    allParentPermissionDescendantPermissionAreChecked = validatePermissionChecked(parentUl);
                }

            });
        });

    }

    const validatePermissionChecked = (parentUl) => {

        if (!parentUl.classList.contains("permission-container")) {

            let countOfChecked = 0;
            let allDirectLiDescendants = parentUl.querySelectorAll("li");

            allDirectLiDescendants = Array.from(allDirectLiDescendants).filter(li => {

                let firstChild = li.firstElementChild;
                return firstChild.tagName.toLowerCase() != "ul";
            });

            let allDirectCheckBoxDescendants = allDirectLiDescendants.map(li => {
                return li.querySelector(".permission-checkbox");
            });

            allDirectCheckBoxDescendants.forEach(checkBox => {
                if (checkBox.checked) {
                    countOfChecked++;
                }
            });

            return countOfChecked == allDirectCheckBoxDescendants.length;
        }
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

    const addPermissionClickEventListener = () => {
        const permissionBtns = document.querySelectorAll(".btn-permission");
        permissionBtns.forEach(permissionBtn => {
            permissionBtn.addEventListener('click', (e) => {
                permissionModal.classList.remove("hidden");
                overlay.classList.remove("hidden");
                currentRoleId = e.target.dataset.roleid;
                loadRolePermissions();
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
        if (isEditing && permissions.includes("ROLE_16")) {
            editRole(e);
        } else if (!isEditing && permissions.includes("ROLE_14")) {
            saveRole(e);
        }
    });
    permissionUpdateBtn.addEventListener('click', (e) => {
        if (permissions.includes("ROLE_18")) {
            updatePermissions(e);
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
    permissionModalCloseBtn.addEventListener('click', () => {
        permissionModal.classList.add("hidden");
        overlay.classList.add("hidden");

        permissionWrapper.innerHTML = "";
    })
    noDelBtn.addEventListener('click', () => {
        deleteModal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    searchBtn.addEventListener('click', () => {
        searchBox.classList.toggle("hidden");
    });
})();