(function() {

    const tableBody = document.querySelector(".table-body");
    const addBtn = document.querySelector(".btn-add");
    const modal = document.querySelector(".modal");
    const overlay = document.querySelector(".overlay");
    const closeBtn = document.querySelector(".btn-close");
    const saveBtn = document.querySelector(".btn-submit");

    const nameInput = document.getElementById("name");
    const priceInput = document.getElementById("price");
    const countInput = document.getElementById("count");

    const init = async () => {
        const products = await fetchProducts();
        products.forEach(product => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${product.productName}</td><td>${product.productPrice}</td><td>${product.productCount}</td><td>${product.productDateAdded}</td><td><button class="btn btn-small btn-edit">Edit</button><button class="btn btn-small btn-delete">Delete</button></td>`;
            tableBody.insertAdjacentElement("beforeend", tr);
        });
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
            dateAdded: new Date().toString().slice(0, 24) 
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

    document.addEventListener('DOMContentLoaded', init);
    addBtn.addEventListener('click', () => {
        modal.classList.remove("hidden");
        overlay.classList.remove("hidden");
    });
    closeBtn.addEventListener('click', () => {
        modal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    saveBtn.addEventListener('click', saveProduct);
    
})();