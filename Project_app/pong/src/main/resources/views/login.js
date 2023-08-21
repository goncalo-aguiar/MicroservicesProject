const loginForm = document.getElementById('login-form');

loginForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const username = event.target.username.value;
    const password = event.target.password.value;

    // Replace with your actual API URL
    axios.post('/login', { username, password })
        .then((response) => {
            const userData = response.data;
            const id = userData.id;
            const bestScore = userData.bestScore;

            // Redirect to the game page and include userId and bestScore as query parameters
            window.location.replace(`http://localhost:9999/pong/pong.html?id=${id}&bestScore=${bestScore}`);

            ;

        })
        .catch((error) => {
            console.log(error);
            alert('Login failed');
        });
});