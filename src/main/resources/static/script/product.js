(function() {
    
    const tableBody = document.querySelector(".table-body");
    const addModal = document.querySelector(".add-modal");
    const deleteModal = document.querySelector(".delete-modal");
    const overlay = document.querySelector(".overlay");
    const searchBox = document.querySelector(".search-box");
    const dialogBox = document.querySelector(".dialog-box");
    const dialogContent = document.querySelector(".dialog-content");
    const cardProductCounter = document.querySelector(".card-product-counter");
    const cardDialog = document.querySelector(".card-modal");
    const orderProductsContainer = document.querySelector(".order-products-container");

    const addBtn = document.querySelector(".btn-add");
    const addModalCloseBtn = document.querySelector(".add-modal-btn-close");
    const deleteModalCloseBtn = document.querySelector(".delete-modal-btn-close");
    const saveBtn = document.querySelector(".btn-submit");
    const searchBtn = document.querySelector(".btn-search");
    const findBtn = document.querySelector(".btn-find");
    const yesDelBtn = document.querySelector(".btn-yes");
    const noDelBtn = document.querySelector(".btn-no");
    const cardBtn = document.querySelector(".card-button");
    const cardDialogCloseBtn = document.querySelector(".card-modal-btn-close");
    const emptyCardBtn = document.querySelector(".btn-empty-card");

    const nameInput = document.getElementById("name");
    const priceInput = document.getElementById("price");
    const countInput = document.getElementById("count");

    const searchNameInput = document.getElementById("search-name");
    const searchPriceInput = document.getElementById("search-price");
    const searchCountInput = document.getElementById("search-count");
    const searchDateAddedInput = document.getElementById("search-date-added");
    const searchDateModifiedInput = document.getElementById("search-date-modified");
    
    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");
    
    let currentProduct;
    let currentProductId;
    let isEditing = false;
    let jwtToken = "";

    const dropdownBtn = document.querySelector(".btn-dropdown");
    const dropdownContent = document.querySelector(".dropdown-content");
    const arrowUp = document.getElementById("arrow-up");
    const arrowDown = document.getElementById("arrow-down");

    let hasPermissionElements = document.querySelectorAll("[data-has-permission]");
    let hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
    let permissions = [];

    let orderProducts = [];

    const init = async () => {

        if (localStorage.getItem("jwt") != null && localStorage.getItem("jwt") != "undefined") {
            jwtToken = "Bearer " + localStorage.getItem("jwt");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");

            permissions = await getUserPermissions();

            if (permissions.includes("ROLE_3")) {
                await fetchProducts();
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

    const fetchProducts = async () => {
        const response = await fetch("http://localhost:8080/api/product", {
            headers: {
                Authorization: jwtToken
            }
        });
        const products = await response.json();

        products.forEach(product => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td class="product-name">${product.productName}</td><td class="product-price">${product.productPrice}</td><td>${product.productCount}</td><td class="hidden" data-has-any-permission="ROLE_4,ROLE_5">${product.productDateAdded}</td><td class="hidden" data-has-any-permission="ROLE_4,ROLE_5">${product.productDateModified}</td><td class="hidden" data-has-any-permission="ROLE_4,ROLE_5"><button class="btn btn-small btn-edit hidden" data-productId="${product.productId}" data-has-permission="ROLE_4">Edit</button><button class="btn btn-small btn-delete hidden" data-productId="${product.productId}" data-has-permission="ROLE_5">Delete</button></td><td class="hidden" data-has-permission="ROLE_121"><button class="btn btn-small btn-add-to-card hidden" data-productId="${product.productId}" data-has-permission="ROLE_121">Add</button><div class="prod-inc-dec-container" data-has-permission="ROLE_121"><span class="minus-sign decrease-product">-</span><input type="number" class="product-count"/><span class="plus-sign increase-product">+</span></div></td>`;
            tr.setAttribute("data-product-id", product.productId);
            tr.setAttribute("data-added-to-card", "false");
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();
        addAddToCardButtonClickEventListener();
        addProductPlusClickEventListener();
        addProductMinusClickEventListener();
    }

    const saveProduct = async (e) => {
        e.preventDefault();

        if (nameInput.value == "" || priceInput.value == "" ||  countInput.value == "") {
            dialogContent.textContent = "Empty Fields Are Not Allowed";
            chooseDialog("error");
            fadeIn();
            return;
        }

        if (priceInput.value < 0) {
            dialogContent.textContent = "Price can not be less than 0";
            chooseDialog("error");
            fadeIn();
            return;
        }

        if (countInput.value < 0) {
            dialogContent.textContent = "Count can not be less than 0";
            chooseDialog("error");
            fadeIn();
            return;
        }

        const productObj = {
            id: null,
            name: nameInput.value,
            price: parseFloat(priceInput.value),
            count: parseInt(countInput.value),
            dateAdded: new Date().toString().slice(0, 24),
            dateModified: new Date().toString().slice(0, 24) 
        }
        const response = await fetch("http://localhost:8080/api/product", {
            body: JSON.stringify(productObj),
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });
        const data = await response.text();
        if (data > 0) {
            dialogContent.textContent = "Product Added Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
        }
    }

    const editProduct = async (e) => {
        e.preventDefault();

        if (nameInput.value == "" || priceInput.value == "" ||  countInput.value == "") {
            dialogContent.textContent = "Empty Fields Are Not Allowed";
            chooseDialog("error");
            fadeIn();
            return;
        }

        if (priceInput.value < 0) {
            dialogContent.textContent = "Price can not be less than 0";
            chooseDialog("error");
            fadeIn();
            return;
        }

        if (countInput.value < 0) {
            dialogContent.textContent = "Count can not be less than 0";
            chooseDialog("error");
            fadeIn();
            return;
        }

        const productObj = {
            id: currentProduct.productId,
            name: nameInput.value,
            price: parseFloat(priceInput.value),
            count: parseInt(countInput.value),
            dateAdded: currentProduct.productDateAdded,
            dateModified: new Date().toString().slice(0, 24) 
        }

        const response = await fetch("http://localhost:8080/api/product", {
            body: JSON.stringify(productObj),
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });

        const data = await response.text();
        if (data > 0) {
            dialogContent.textContent = "Product Edited Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
        }
        
    }

    const delProduct = async () => {
        
        const response = await fetch(`http://localhost:8080/api/product/${currentProductId}`, {
            method: 'DELETE',
            headers: {
                Authorization: jwtToken
            }
        })
        const data = await response.text();

        if (response.status == 400) {
            dialogContent.textContent = "Operation Not Successful";
            chooseDialog("error");
            fadeIn();
        } else if (data === "true") {
            dialogContent.textContent = "Product Deleted Successfully";
            chooseDialog("info");
            fadeIn();
            
            setTimeout(() => window.location.reload(), 3000);
        }
    }

    const find = async () => {

        if (searchPriceInput.value < 0) {
            dialogContent.textContent = "Price can not be less than 0";
            chooseDialog("error");
            fadeIn();
            return;
        }

        if (searchCountInput.value < 0) {
            dialogContent.textContent = "Count can not be less than 0";
            chooseDialog("error");
            fadeIn();
            return;
        }

        const searchProductObj = {
            id: null,
            name: searchNameInput.value,
            price: parseFloat(searchPriceInput.value),
            count: parseInt(searchCountInput.value),
            dateAdded: searchDateAddedInput.value,
            dateModified: searchDateModifiedInput.value
        };

        const response = await fetch ("http://localhost:8080/api/product/search", {
            method: 'POST',
            body: JSON.stringify(searchProductObj),
            headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken
            }
        });
        const data = await response.json();

        tableBody.innerHTML = "";
        data.forEach(product => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td class="product-name">${product.productName}</td><td class="product-price">${product.productPrice}</td><td>${product.productCount}</td><td class="hidden" data-has-any-permission="ROLE_4,ROLE_5">${product.productDateAdded}</td><td class="hidden" data-has-any-permission="ROLE_4,ROLE_5">${product.productDateModified}</td><td class="hidden" data-has-any-permission="ROLE_4,ROLE_5"><button class="btn btn-small btn-edit hidden" data-productId="${product.productId}" data-has-permission="ROLE_4">Edit</button><button class="btn btn-small btn-delete hidden" data-productId="${product.productId}" data-has-permission="ROLE_5">Delete</button></td><td class="hidden" data-has-permission="ROLE_121"><button class="btn btn-small btn-add-to-card hidden" data-productId="${product.productId}" data-has-permission="ROLE_121">Add</button><div class="prod-inc-dec-container" data-has-permission="ROLE_121"><span class="minus-sign decrease-product">-</span><input type="number" class="product-count"/><span class="plus-sign increase-product">+</span></div></td></td>`;
            tr.setAttribute("data-product-id", product.productId);
            tr.setAttribute("data-added-to-card", "false");
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
        addDeleteClickEventListener();
        addAddToCardButtonClickEventListener();
        addProductPlusClickEventListener();
        addProductMinusClickEventListener();

        hasPermissionElements = document.querySelectorAll("[data-has-permission]");
        hasAnyPermissionsElements = document.querySelectorAll("[data-has-any-permission]");
        auditPermissions();
    };

    const loadProduct = async (e) => {
        const response = await fetch(`http://localhost:8080/api/product/${e.target.dataset.productid}`, {
            headers: {
                Authorization: jwtToken
            }
        });
        currentProduct = await response.json();
        
        addModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Edit";
        nameInput.value = currentProduct.productName;
        priceInput.value = currentProduct.productPrice;
        countInput.value = currentProduct.productCount;
        isEditing = true;

    }

    const processCard = async () => {
        orderProducts = [];
        const allProductsAddedToCard = document.querySelectorAll("[data-added-to-card='true']");
        allProductsAddedToCard.forEach(addedProduct => {
            const orderProductObj = {
                id: null,
                productId: parseInt(addedProduct.dataset.productId),
                product: {
                    id: parseInt(addedProduct.dataset.productId),
                    name: addedProduct.querySelector(".product-name").textContent
                },
                price: parseInt(addedProduct.querySelector(".product-price").textContent),
                count: parseInt(addedProduct.querySelector(".product-count").value),
                totalPrice: parseInt(addedProduct.querySelector(".product-price").textContent) * parseInt(addedProduct.querySelector(".product-count").value) 
            };
            orderProducts.push(orderProductObj);
        })
        console.log(orderProducts);
    }

    const openCardDialog = () => {
        let orderProductsContainerHtml = ``;
        orderProducts.forEach(orderProduct => {
            let orderProductHtml = `<div class="order-product"><span class="delete-order-product"><i class="bx bxs-trash"></i></span><div class="order-product-grid"><p class="order-product-name">Item: ${orderProduct.product.name}</p><span></span><p class="order-product-total-price">Total Item Price: $<span class="order-product-total-price-number">${orderProduct.totalPrice}</span></p><div class="prod-inc-dec-container"><span class="minus-sign decrease-product">-</span><input type="number" class="product-count" value="${orderProduct.count}"/><span class="plus-sign increase-product">+</span></div></div></div>`;
            orderProductsContainerHtml += orderProductHtml;
        });
        orderProductsContainer.innerHTML = orderProductsContainerHtml;
        overlay.classList.remove("hidden");
        cardDialog.classList.remove("hidden");
        updateOrderTotalItemsAndPrice();
        addDeleteOrderProductClickEventListener();
    }

    const emptyCard = () => {
        orderProducts = [];
        const allAddedToCardProducts = document.querySelectorAll("[data-added-to-card='true']");
        allAddedToCardProducts.forEach(addedToCardProduct => {
            addedToCardProduct.setAttribute("data-added-to-card", false);
            addedToCardProduct.querySelector(".prod-inc-dec-container").style.display = "none";
            addedToCardProduct.querySelector(".btn-add-to-card").style.display = "table-cell";
        })
        cardBtn.classList.remove("card-button-active");
        cardProductCounter.textContent = "0";
        cardDialog.classList.add("hidden");
        overlay.classList.add("hidden");
    }

    const updateOrderTotalItemsAndPrice = () => {
        const cardOrderProducts = cardDialog.querySelectorAll(".order-product");
        let totalOrderItems = 0;
        let totalOrderPrice = 0;
        cardOrderProducts.forEach(cardOrderProduct => {
            let orderProductCount = parseInt(cardOrderProduct.querySelector(".product-count").value);
            let orderProductTotalPrice = parseInt(cardOrderProduct.querySelector(".order-product-total-price-number").textContent);
            
            totalOrderItems += orderProductCount;
            totalOrderPrice += orderProductTotalPrice;
        });
        cardDialog.querySelector(".order-total-items-number").textContent = totalOrderItems;
        cardDialog.querySelector(".order-total-price-number").textContent = totalOrderPrice;
    }

    const addEditClickEventListener = () => {
        const editBtns = document.querySelectorAll(".btn-edit");
        editBtns.forEach(editBtn => {
            editBtn.addEventListener('click', (e) => {
                if (permissions.includes("ROLE_3")) {
                    loadProduct(e);
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
                currentProductId = e.target.dataset.productid;
            });
        })
    }

    const addAddToCardButtonClickEventListener = () => {
        const addToCardBtns = document.querySelectorAll(".btn-add-to-card");
        addToCardBtns.forEach(addToCardBtn => {
            addToCardBtn.addEventListener('click', (e) => {

                const productRow = addToCardBtn.parentElement.parentElement;
                productRow.setAttribute("data-added-to-card", "true");

                cardProductCounter.textContent = parseInt(cardProductCounter.textContent) + 1;

                addToCardBtn.style.display = "none";
                if (!cardBtn.classList.contains("card-button-active")) {
                    cardBtn.classList.add("card-button-active");
                }

                const prodIncDecContainer = addToCardBtn.nextElementSibling;
                prodIncDecContainer.querySelector(".product-count").value = 1;
                prodIncDecContainer.style.display = "block";

            })
        })
    }

    const addProductPlusClickEventListener = () => {
        const allIncreasers = document.querySelectorAll(".increase-product");
        allIncreasers.forEach(increaser => {
            increaser.addEventListener('click', (e) => {

                let productCurrentCount = parseInt(increaser.previousElementSibling.value);
                let productAvailableCount = parseInt(increaser.parentElement.parentElement.previousElementSibling.textContent);

                if (productCurrentCount < productAvailableCount) {
                    increaser.previousElementSibling.value = ++productCurrentCount;
                }

                if (productCurrentCount > productAvailableCount) {
                    increaser.previousElementSibling.value = productAvailableCount;
                }
                
            })
        })
    }

    const addProductMinusClickEventListener = () => {
        const allDecreasers = document.querySelectorAll(".decrease-product");
        allDecreasers.forEach(decreaser => {
            decreaser.addEventListener('click', (e) => {

                const productRow = decreaser.parentElement.parentElement.parentElement;

                const prodIncDecContainer = decreaser.parentElement;
                const addToCardBtn = prodIncDecContainer.previousElementSibling;

                let productCurrentCount = parseInt(decreaser.nextElementSibling.value);
                let productAvailableCount = parseInt(decreaser.parentElement.parentElement.previousElementSibling.textContent);
            
                if (productCurrentCount > productAvailableCount) {
                    decreaser.nextElementSibling.value = productAvailableCount - 1;
                } else if (productCurrentCount > 1) {
                    decreaser.nextElementSibling.value = --productCurrentCount;
                } else {
                    productRow.setAttribute("data-added-to-card", "false");
                    cardProductCounter.textContent = (parseInt(cardProductCounter.textContent) > 1) ? parseInt(cardProductCounter.textContent) - 1 : 0;
                    if (parseInt(cardProductCounter.textContent) == 0) {
                        cardBtn.classList.remove("card-button-active");
                    }
                    prodIncDecContainer.style.display = "none";
                    addToCardBtn.style.display = "table-cell";
                }

            })
        })
    }

    const addDeleteOrderProductClickEventListener = () => {
        const allDeleteOrderProductBtns = document.querySelectorAll(".delete-order-product");
        allDeleteOrderProductBtns.forEach(deleteOrderProductBtn => {
            deleteOrderProductBtn.addEventListener('click', () => {
                const orderProduct = deleteOrderProductBtn.parentElement;
                orderProduct.remove();
                updateOrderTotalItemsAndPrice();
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
        if (isEditing && permissions.includes("ROLE_4")) {
            editProduct(e);
        } else if (!isEditing && permissions.includes("ROLE_2")) {
            saveProduct(e);
        }
    });
    dropdownBtn.addEventListener('click', dropdownContentToggle);
    findBtn.addEventListener('click', () => {
        if (permissions.includes("ROLE_3")) {
            find();
        }
    });
    yesDelBtn.addEventListener('click', () => {
        if (permissions.includes("ROLE_5")) {
            delProduct();
        }
    });
    logoutBtn.addEventListener('click', logout);

    addBtn.addEventListener('click', () => {
        addModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Save";
        nameInput.value = "";
        priceInput.value = "";
        countInput.value = "";
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
    cardBtn.addEventListener('click', async () => {
        if (permissions.includes("ROLE_121")) {
            await processCard();
            openCardDialog();
        }
    });
    cardDialogCloseBtn.addEventListener('click', () => {
        cardDialog.classList.add("hidden");
        overlay.classList.add("hidden");
    });
    emptyCardBtn.addEventListener('click', () => {
        emptyCard();
    })
})();