(function() {

    const signupForm = document.querySelector(".signup-form");
    const loggedinText =  document.querySelector(".loggedin-text");
    const usernameExists = document.querySelector(".username-exists");
    const firstNameInput = document.getElementById("firstName");
    const lastNameInput = document.getElementById("lastName");
    const usernameInput = document.getElementById("username");
    const passwordInput = document.getElementById("password");
    const nameContainer = document.querySelector(".name-container");
    const name = document.querySelector(".name");
    const signupBtn = document.querySelector(".btn-signup");
    const navSignup = document.querySelector(".nav-signup");
    const navLogin = document.querySelector(".nav-login");
    const navLogout = document.querySelector(".nav-logout");
    const navProducts = document.querySelector(".nav-products");
    const logoutBtn = document.querySelector(".logout");
    const haveAccount = document.querySelector(".have-account");
    
    let jwtToken = "";

    const init = async () => {
        jwtToken = localStorage.getItem("jwt");

        if (jwtToken != null && jwtToken != "undefined") {
            signupForm.classList.add("hidden");
            navSignup.classList.add("hidden");
            navLogin.classList.add("hidden");
            navLogout.classList.remove("hidden");
            navProducts.classList.remove("hidden");
            loggedinText.classList.remove("hidden");
            nameContainer.classList.remove("hidden");
            haveAccount.classList.add("hidden");

            const userData = await getUserData();
            name.textContent = userData.userFirstName + " " + userData.userLastName;

        } else {
            signupForm.classList.remove("hidden");
            navSignup.classList.remove("hidden");
            navLogin.classList.remove("hidden");
            navLogout.classList.add("hidden");
            navProducts.classList.add("hidden");
            loggedinText.classList.add("hidden");
            nameContainer.classList.add("hidden");
            haveAccount.classList.remove("hidden");

            name.textContent = "";
        }

    }

    const signup = async (e) => {
        e.preventDefault();

        const userObj = {
            username: usernameInput.value,
            password: passwordInput.value,
            userFirstName: firstNameInput.value,
            userLastName: lastNameInput.value
        }
        
        const signupResponse = await fetch("http://localhost:8080/signup", {
            method: 'POST',
            body: JSON.stringify(userObj),
            headers: {
                "Content-Type": "application/json"
            }
        })

        if (signupResponse.status == 400) {
            usernameExists.classList.remove("hidden");
            setTimeout(() => {
                usernameExists.classList.add("hidden");
            }, 3000);
            return;
        }

        const data = await signupResponse.json();

        if (data > 0) {

            const authReq = {
                username: usernameInput.value,
                password: passwordInput.value
            }

            const loginResponse = await fetch("http://localhost:8080/login", {
                method: 'POST',
                body: JSON.stringify(authReq),
                headers: {
                    "Content-Type": "application/json"
                }
            });

            const data = await loginResponse.json();

            localStorage.setItem("jwt", data.jwtToken);
            window.location.reload();
        }
    }

    const logout = () => {
        localStorage.removeItem("jwt");
        window.location.replace("/html/index.html");
    }

    const getUserData = async () => {
        const response = await fetch("http://localhost:8080/api/account", {
            headers: {
                Authorization: "Bearer " + jwtToken
            }
        })
        const data = await response.json();
        return data;
    }

    document.addEventListener('DOMContentLoaded', init);
    signupBtn.addEventListener('click', signup);
    logoutBtn.addEventListener('click', logout);


})();