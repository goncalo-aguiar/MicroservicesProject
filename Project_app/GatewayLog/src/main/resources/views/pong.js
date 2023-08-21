// select canvas element
const canvas = document.getElementById("pong");

// getContext of canvas = methods and properties to draw and do a lot of thing to the canvas
const ctx = canvas.getContext('2d');
const urlParams = new URLSearchParams(window.location.search);
const jwtToken = getCookie('token');
// Use the token as needed
const id = decodeJWT(jwtToken);
console.log(id);
//const bestScore = parseInt(urlParams.get('bestScore'));
//const  bestScore =  getBestScore(id);
const  bestScore =  0;


/*
function getBestScore(id) {
    fetch(`http://localhost:9999/get_user_score/${id}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data); // do something with the response data
            return data;

        })
        .catch(error => console.error(error));
}


*/



function decodeJWT(token) {
    const base64Url = token.split('.')[1]; // Extract the base64 encoded payload
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // Replace URL-safe characters
    const decodedPayload = window.atob(base64); // Decode the base64 payload

    const payload = JSON.parse(decodedPayload); // Parse the JSON payload
    const username = payload.sub; // Extract the username from the "sub" field

    return username;
}

// Function to get the value of a specific cookie by name
function getCookie(name) {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith(name + '=')) {
            return cookie.substring(name.length + 1);
        }
    }
    return null;
}

// Ball object
const ball = {
    x : canvas.width/2,
    y : canvas.height/2,
    radius : 10,
    velocityX : 5,
    velocityY : 5,
    speed : 7,
    color : "WHITE"
}

// User Paddle
const user = {
    x : 0, // left side of canvas
    y : (canvas.height - 100)/2, // -100 the height of paddle
    width : 10,
    height : 100,
    score : 0,
    best_score : bestScore,
    color : "WHITE"
}

// COM Paddle
const com = {
    x : canvas.width - 10, // - width of paddle
    y : (canvas.height - 100)/2, // -100 the height of paddle
    width : 10,
    height : 100,
    score : 0,
    color : "WHITE"
}

// NET
const net = {
    x : (canvas.width - 2)/2,
    y : 0,
    height : 10,
    width : 2,
    color : "WHITE"
}

// draw a rectangle, will be used to draw paddles
function drawRect(x, y, w, h, color){
    ctx.fillStyle = color;
    ctx.fillRect(x, y, w, h);
}

// draw circle, will be used to draw the ball
function drawArc(x, y, r, color){
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.arc(x,y,r,0,Math.PI*2,true);
    ctx.closePath();
    ctx.fill();
}

let loop;
let gameOver = false;
let fps = 60;
let frameDuration = 1000 / fps;
let lastFrameTime = 0;



// listening to the mouse
canvas.addEventListener("mousemove", getMousePos);

document.addEventListener("DOMContentLoaded", function() {document.getElementById("startGameBtn").addEventListener("click", startGame);
});

function getMousePos(evt) {
    let rect = canvas.getBoundingClientRect();
    let newPosY = evt.clientY - rect.top - user.height / 2;

    if (newPosY < 0) {
        user.y = 0;
    } else if (newPosY + user.height > canvas.height) {
        user.y = canvas.height - user.height;
    } else {
        user.y = newPosY;
    }
}

// when COM or USER scores, we reset the ball
function resetBall() {
    // Set the ball position to the center
    ball.x = canvas.width / 2;
    ball.y = canvas.height / 2;

    // Store the current ball velocity


    // Set the ball velocity to zero
    ball.velocityX = 0;
    ball.velocityY = 0;

    // Set the ball speed
    ball.speed = 7;

    // Wait for 1 second before the ball starts moving again
    setTimeout(() => {
        // Restore the ball velocity
        ball.velocityX = 5;
        ball.velocityY = 5;
    }, 1000);
}


// draw the net
function drawNet(){
    for(let i = 0; i <= canvas.height; i+=15){
        drawRect(net.x, net.y + i, net.width, net.height, net.color);
    }
}

// draw text
function drawText(text,x,y){
    ctx.fillStyle = "#FFF";
    ctx.font = "20px fantasy";
    ctx.fillText(text, x, y);
}

// collision detection
function collision(b,p){
    p.top = p.y;
    p.bottom = p.y + p.height;
    p.left = p.x;
    p.right = p.x + p.width;

    b.top = b.y - b.radius;
    b.bottom = b.y + b.radius;
    b.left = b.x - b.radius;
    b.right = b.x + b.radius;

    return p.left < b.right && p.top < b.bottom && p.right > b.left && p.bottom > b.top;
}

// update function, the function that does all calculations
function update(){

    // change the score of players, if the ball goes to the left "ball.x<0" computer win, else if "ball.x > canvas.width" the user win
    if( ball.x - ball.radius < 0 ){
        com.score++;

        resetBall();
    }else if( ball.x + ball.radius > canvas.width){
        user.score++;
        if (user.score > user.best_score){
            user.best_score = user.score;

        }

        resetBall();
    }



    // the ball has a velocity
    ball.x += ball.velocityX;
    ball.y += ball.velocityY;

    // computer plays for itself, and we must be able to beat it
    // simple AI
    com.y += ((ball.y - (com.y + com.height/2)))*0.01;

    // when the ball collides with bottom and top walls we inverse the y velocity.
    if(ball.y - ball.radius < 0 || ball.y + ball.radius > canvas.height){
        ball.velocityY = -ball.velocityY;

    }

    // we check if the paddle hit the user or the com paddle
    let player = (ball.x + ball.radius < canvas.width/2) ? user : com;

    // if the ball hits a paddle
    if(collision(ball,player)){

        // we check where the ball hits the paddle
        let collidePoint = (ball.y - (player.y + player.height/2));
        // normalize the value of collidePoint, we need to get numbers between -1 and 1.
        // -player.height/2 < collide Point < player.height/2
        collidePoint = collidePoint / (player.height/2);

        // when the ball hits the top of a paddle we want the ball, to take a -45degees angle
        // when the ball hits the center of the paddle we want the ball to take a 0degrees angle
        // when the ball hits the bottom of the paddle we want the ball to take a 45degrees
        // Math.PI/4 = 45degrees
        let angleRad = (Math.PI/4) * collidePoint;

        // change the X and Y velocity direction
        let direction = (ball.x + ball.radius < canvas.width/2) ? 1 : -1;
        ball.velocityX = direction * ball.speed * Math.cos(angleRad);
        ball.velocityY = ball.speed * Math.sin(angleRad);

        // speed up the ball everytime a paddle hits it.
        ball.speed += 0.1;
    }
    if (com.score >= 1) {
        gameOver = true;
        clearInterval(loop);

    }

}

// the function that does al the drawing
function render(){

    // clear the canvas
    drawRect(0, 0, canvas.width, canvas.height, "#000");

    // draw the user score to the left
    drawText("User score: " + user.score,3*canvas.width/4 - 25,canvas.height/10);
    drawText("User best score: " + user.best_score,3*canvas.width/4 - 25,canvas.height/10 + 30);
    // draw the COM score to the right    // drawText(com.score,3*canvas.width/4,canvas.height/5);

    // draw the net
    drawNet();

    // draw the user's paddle
    drawRect(user.x, user.y, user.width, user.height, user.color);

    // draw the COM's paddle
    drawRect(com.x, com.y, com.width, com.height, com.color);

    // draw the ball
    drawArc(ball.x, ball.y, ball.radius, ball.color);
}
function game(currentTime) {
    if (!gameOver) {
        // Calculate the elapsed time since the last frame
        let elapsedTime = currentTime - lastFrameTime;

        // Check if the required time has passed for the desired FPS
        if (elapsedTime >= frameDuration) {
            update();
            render();
            lastFrameTime = currentTime;
        }

        requestAnimationFrame(game);
    } else {
        gameOverScreen();
    }
}

function startGame() {
    // Disable the start game button
    document.getElementById("startGameBtn").disabled = true;
    gameOver = false;
    com.score = 0;
    user.score = 0;
    resetBall();
    // Start the game loop
    requestAnimationFrame(game);
}
function gameOverScreen() {
    // clear the canvas
    drawRect(0, 0, canvas.width, canvas.height, "#000");

    if (user.best_score > bestScore){
        updateBestScore(id, user.best_score);}
    // display the game over message
    ctx.fillStyle = "#FFF";
    ctx.font = "50px fantasy";
    ctx.textAlign = "center";
    ctx.fillText("Game Over", canvas.width / 2, canvas.height / 2 - 50);

    // display the final scores
    ctx.font = "30px fantasy";
    ctx.fillText("Final Scores User " + id, canvas.width / 2, canvas.height / 2);
    ctx.fillText("Score: " + user.score, canvas.width / 2, canvas.height / 2 + 40);
    ctx.fillText("Best score: " + user.best_score, canvas.width / 2, canvas.height / 2 + 80);

    document.getElementById("startGameBtn").disabled = false;
}




/*function updateBestScore(userId, newBestScore) {
    console.log(userId);
    fetch(`http://localhost:9999/updateScore/${userId}/${newBestScore}`, {
        method: "POST"
    })
        .then(response => response.json())
        .then(player => {
            console.log(player); // do something with the player object
        })
        .catch(error => console.error(error));
}*/
function updateBestScore(userId, newBestScore) {
    console.log(userId);

    const requestBody = {
        id: userId,
        score: newBestScore
    };
    const authHeader = `Bearer ${jwtToken}`;

    fetch(`http://localhost:9999/update-best-score/${userId}/${newBestScore}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": authHeader
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => response.json())
        .then(data => {
            console.log(data); // do something with the response data
        })
        .catch(error => console.error(error));
}




