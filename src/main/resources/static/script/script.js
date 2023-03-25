(function() {

    const fetchHome = async () => {
        const response = await fetch("http://localhost:8080");
        const data = await response.text();
        document.getElementById("date").textContent = data;
    }

    document.addEventListener('DOMContentLoaded', fetchHome);
})();