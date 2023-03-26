(function() {

    const tableBody = document.querySelector(".table-body");
    const addBtn = document.querySelector(".btn-add");
    const modal = document.querySelector(".modal");
    const overlay = document.querySelector(".overlay");
    const closeBtn = document.querySelector(".btn-close");

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

    document.addEventListener('DOMContentLoaded', init);
    addBtn.addEventListener('click', () => {
        modal.classList.remove("hidden");
        overlay.classList.remove("hidden");
    });
    closeBtn.addEventListener('click', () => {
        modal.classList.add("hidden");
        overlay.classList.add("hidden");
    })
    
})();