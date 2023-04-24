(function() {

    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");
    const navSignup = document.querySelector(".nav-signup");
    const navProducts = document.querySelector(".nav-products");
    const navManagement = document.querySelector(".nav-management");
    const dropdownBtn = document.querySelector(".btn-dropdown");
    const dropdownContent = document.querySelector(".dropdown-content");
    const arrowUp = document.getElementById("arrow-up");
    const arrowDown = document.getElementById("arrow-down");

    const fetchHome = async () => {

        if (localStorage.getItem("jwt") != null && localStorage.getItem("jwt") != "undefined") {
            navLogin.classList.add("hidden");
            navProducts.classList.remove("hidden");
            navSignup.classList.add("hidden");
            navLogout.classList.remove("hidden");
            navManagement.classList.remove("hidden");
        }

        const response = await fetch("http://localhost:8080");
        const data = await response.text();
        document.getElementById("date").textContent = data;
    }

    const dropdownContentToggle = () => {
        dropdownContent.classList.toggle("hidden");
        arrowUp.classList.toggle("hidden");
        arrowDown.classList.toggle("hidden");

    }

    const logout = () => {
        localStorage.removeItem("jwt");
        window.location.reload();
    }

    document.addEventListener('DOMContentLoaded', fetchHome);
    dropdownBtn.addEventListener('click', dropdownContentToggle);
    logoutBtn.addEventListener('click', logout);

})();