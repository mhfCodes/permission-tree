(function() {

    const tableBody = document.querySelector(".table-body");

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
})();