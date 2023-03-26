(function() {
    
    const tableBody = document.querySelector(".table-body");
    const modal = document.querySelector(".modal");
    const overlay = document.querySelector(".overlay");
    const searchBox = document.querySelector(".search-box");

    const addBtn = document.querySelector(".btn-add");
    const closeBtn = document.querySelector(".btn-close");
    const saveBtn = document.querySelector(".btn-submit");
    const searchBtn = document.querySelector(".btn-search");
    const findBtn = document.querySelector(".btn-find");

    const nameInput = document.getElementById("name");
    const priceInput = document.getElementById("price");
    const countInput = document.getElementById("count");

    const searchNameInput = document.getElementById("search-name");
    const searchPriceInput = document.getElementById("search-price");
    const searchCountInput = document.getElementById("search-count");
    const searchDateAddedInput = document.getElementById("search-date-added");
    const searchDateModifiedInput = document.getElementById("search-date-modified");
    
    let currentProduct;
    let isEditing = false;

    const init = async () => {
        const products = await fetchProducts();
        products.forEach(product => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${product.productName}</td><td>${product.productPrice}</td><td>${product.productCount}</td><td>${product.productDateAdded}</td><td>${product.productDateModified}</td><td><button class="btn btn-small btn-edit" data-productId="${product.productId}">Edit</button><button class="btn btn-small btn-delete" data-productId="${product.productId}">Delete</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        
        addEditClickEventListener();
    }

    const fetchProducts = async () => {
        const response = await fetch("http://localhost:8080/api/product");
        const data = await response.json();
        return data;
    }

    const saveProduct = async (e) => {
        e.preventDefault();

        if (priceInput.value < 0) {
            alert("Price can not be less than 0");
            throw new Error("Price can not be less than 0");
        }

        if (countInput.value < 0) {
            alert("Count can not be less than 0");
            throw new Error("Count can not be less than 0");
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
                "Content-Type": "application/json"
            }
        });
        const data = await response.text();
        if (data > 0) {
            window.location.reload();
        }
    }

    const editProduct = async (e) => {
        e.preventDefault();

        if (priceInput.value < 0) {
            alert("Price can not be less than 0");
            throw new Error("Price can not be less than 0");
        }

        if (countInput.value < 0) {
            alert("Count can not be less than 0");
            throw new Error("Count can not be less than 0");
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
                "Content-Type": "application/json"
            }
        });

        const data = await response.text();
        if (data > 0) {
            window.location.reload();
        }
        
    }

    const find = async () => {

        if (searchPriceInput.value < 0) {
            alert("Price can not be less than 0");
            throw new Error("Price can not be less than 0");
        }

        if (searchCountInput.value < 0) {
            alert("Count can not be less than 0");
            throw new Error("Count can not be less than 0");
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
                "Content-Type": "application/json"
            }
        });
        const data = await response.json();

        tableBody.innerHTML = "";
        data.forEach(product => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${product.productName}</td><td>${product.productPrice}</td><td>${product.productCount}</td><td>${product.productDateAdded}</td><td>${product.productDateModified}</td><td><button class="btn btn-small btn-edit" data-productId="${product.productId}">Edit</button><button class="btn btn-small btn-delete" data-productId="${product.productId}">Delete</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
        addEditClickEventListener();
    };

    const loadProduct = async (e) => {
        const response = await fetch(`http://localhost:8080/api/product/${e.target.dataset.productid}`);
        currentProduct = await response.json();
        
        modal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Edit";
        nameInput.value = currentProduct.productName;
        priceInput.value = currentProduct.productPrice;
        countInput.value = currentProduct.productCount;
        isEditing = true;

    }

    const addEditClickEventListener = () => {
        const editBtns = document.querySelectorAll(".btn-edit");
        editBtns.forEach(editBtn => {
            editBtn.addEventListener('click', loadProduct);
        })
    }

    document.addEventListener('DOMContentLoaded', init);
    saveBtn.addEventListener('click', (e) => isEditing ? editProduct(e) : saveProduct(e));
    findBtn.addEventListener('click', find);

    addBtn.addEventListener('click', () => {
        modal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        saveBtn.textContent = "Save";
        nameInput.value = "";
        priceInput.value = "";
        countInput.value = "";
        isEditing = false;
    });
    closeBtn.addEventListener('click', () => {
        modal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    searchBtn.addEventListener('click', () => {
        searchBox.classList.toggle("hidden");
    });
})();