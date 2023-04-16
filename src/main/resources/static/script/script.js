(function() {

    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const logoutBtn = document.querySelector(".logout");
    const navSignup = document.querySelector(".nav-signup");
    const navProducts = document.querySelector(".nav-products");

    const fetchHome = async () => {

        if (localStorage.getItem("jwt") != null && localStorage.getItem("jwt") != "undefined") {
            navLogin.classList.add("hidden");
            navProducts.classList.remove("hidden");
            navSignup.classList.add("hidden");
            navLogout.classList.remove("hidden");
        }

        const response = await fetch("http://localhost:8080");
        const data = await response.text();
        document.getElementById("date").textContent = data;
    }

    const logout = () => {
        localStorage.removeItem("jwt");
        window.location.reload();
    }

    document.addEventListener('DOMContentLoaded', fetchHome);
    logoutBtn.addEventListener('click', logout);

})();