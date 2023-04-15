(function() {

    const signupBox = document.querySelector(".signup-box");
    const loggedinText =  document.querySelector(".loggedin-text");
    const usernameExists = document.querySelector(".username-exists");
    const firstNameInput = document.getElementById("firstName");
    const lastNameInput = document.getElementById("lastName");
    const usernameInput = document.getElementById("username");
    const passwordInput = document.getElementById("password");
    const signupBtn = document.querySelector(".btn-signup");
    
    let jwtToken = "";

    const init = () => {
        jwtToken = localStorage.getItem("jwt");

        if (jwt != null && jwt != "undefined") {
            signupBox.classList.add("hidden");
            loggedinText.classList.remove("hidden");
        } else {
            signupBox.classList.remove("hidden");
            loggedinText.classList.add("hidden");
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

    document.addEventListener('DOMContentLoaded', init);
    signupBtn.addEventListener('click', signup);

})();