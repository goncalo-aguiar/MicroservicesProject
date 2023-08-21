var turns = [["#", "#", "#"], ["#", "#", "#"], ["#", "#", "#"]];
var turn = "";
var gameOn = true;
var playerType;
var computerType;

var loginValue;
function playerTurn(turn, id) {
    if (gameOn && turn === playerType) {
        var spotTaken = $("#" + id).text();
        if (spotTaken === "#") {
            $("#" + id).text(turn);
            var aux = id.split("_");
            turns[aux[0]][aux[1]] = turn;
            console.log(turns);
            checkWinner(turn);
            if (gameOn) {
                turn = computerType;
                computerTurn(turn);
            }
        }
    }
}



function computerTurn(turn) {

    // Get all empty spots on the board
    var emptySpots = getEmptySpots();

    // Check if the computer can win on the next move
    for (var i = 0; i < emptySpots.length; i++) {
        var aux = emptySpots[i].split("_");

        turns[aux[0]][aux[1]] = turn;
        if (checkWinner2() == turn) {
            $("#" + aux[0] + "_" + aux[1]).text(turn);
            setTimeout(checkWinner, 500);
            return;
        }
        turns[aux[0]][aux[1]] = "#";
    }

    // Check if the player can win on the next move and block them
    var opponentTurn = turn == "X" ? "O" : "X";
    for (var i = 0; i < emptySpots.length; i++) {
        var aux = emptySpots[i].split("_");

        turns[aux[0]][aux[1]] = opponentTurn;

        if (checkWinner2() == opponentTurn) {
            $("#" +aux[0] + "_" + aux[1]).text(turn);
            turns[aux[0]][aux[1]] = turn;
            setTimeout(checkWinner, 500);
            return;
        }
        turns[aux[0]][aux[1]] = "#";
    }
    // If there are no immediate winning or blocking moves, choose a random spot
    var randomIndex = Math.floor(Math.random() * emptySpots.length);
    var id = emptySpots[randomIndex];
    var aux = id.split("_");
    $("#" + id).text(turn);
    turns[aux[0]][aux[1]] = turn;
    console.log(turns);

    setTimeout(checkWinner, 500);

    // Update the turn variable
    turn = playerType;
}

function getEmptySpots() {
    var emptySpots = [];
    for (var i = 0; i < 3; i++) {
        for (var j = 0; j < 3; j++) {
            if (turns[i][j] === "#") {
                emptySpots.push(i + "_" + j);
            }
        }
    }
    return emptySpots;
}



function checkWinner() {
    // Check rows
    for (let i = 0; i < 3; i++) {
        if (turns[i][0] === turns[i][1] && turns[i][1] === turns[i][2] && turns[i][0] !== "#") {
            gameOn = false;
            sendInfo(turns[i][0]);
            alert("Winner is " + turns[i][0]);
            return;
        }
    }
    // Check columns
    for (let i = 0; i < 3; i++) {
        if (turns[0][i] === turns[1][i] && turns[1][i] === turns[2][i] && turns[0][i] !== "#") {
            gameOn = false;
            sendInfo(turns[0][i]);
            alert("Winner is " + turns[0][i]);
            return;
        }
    }
    // Check diagonals
    if (turns[0][0] === turns[1][1] && turns[1][1] === turns[2][2] && turns[0][0] !== "#") {
        gameOn = false;
        sendInfo(turns[0][0]);
        alert("Winner is " + turns[0][0]);
        return;
    }
    if (turns[0][2] === turns[1][1] && turns[1][1] === turns[2][0] && turns[0][2] !== "#") {
        gameOn = false;
        sendInfo(turns[0][2]);
        alert("Winner is " + turns[0][2]);
        return;
    }
    // Check tie game
    let moves = 0;
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (turns[i][j] !== "#") {
                moves++;
            }
        }
    }
    if (moves === 9) {
        gameOn = false;
        sendInfo("T");
        alert("Tie game!");
    }
}

function checkWinner2() {
    // Check rows
    for (let i = 0; i < 3; i++) {
        if (turns[i][0] === turns[i][1] && turns[i][1] === turns[i][2] && turns[i][0] !== "#") {


            return turns[i][0];
        }
    }
    // Check columns
    for (let i = 0; i < 3; i++) {
        if (turns[0][i] === turns[1][i] && turns[1][i] === turns[2][i] && turns[0][i] !== "#") {


            return turns[0][i];
        }
    }
    // Check diagonals
    if (turns[0][0] === turns[1][1] && turns[1][1] === turns[2][2] && turns[0][0] !== "#") {


        return turns[0][0];
    }
    if (turns[0][2] === turns[1][1] && turns[1][1] === turns[2][0] && turns[0][2] !== "#") {

        return turns[0][2];
    }
    // Check tie game
    let moves = 0;
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (turns[i][j] !== "#") {
                moves++;
            }
        }
    }
    if (moves === 9) {

        return "#"
    }
}
function sendInfo(x){



    const jwtToken = getCookie('token');

// Use the token as needed


    const username = decodeJWT(jwtToken);
    console.log('JWT token:', username);
    let win_lose = 0;
    if (x=="T"){
        win_lose = 0;
    }
    else if (x == playerType ){
        win_lose = 1;
    }
    else{
        win_lose = 0;
    }

    const authHeader = `Bearer ${jwtToken}`;
    fetch(`http://localhost:8090/players/${username}/${win_lose}`, {
        headers: {
            "Authorization": authHeader
        }
    })
        .then(response => response.json())
        .then(player => {
            console.log(player); // do something with the player object
        })
        .catch(error => console.error(error));

}

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




$(".tic").click(function () {
    var slot = $(this).attr('id');
    playerTurn(turn, slot);
});

function reset() {
    turns = [["#", "#", "#"], ["#", "#", "#"], ["#", "#", "#"]];
    $(".tic").text("#");
    gameOn = true;
    turn = playerType;
}

$("#reset").click(function () {
    reset();
});

function create_game() {


    if (Math.random() < 0.5) {
        playerType = "X";
        computerType = "O";

    } else {
        playerType = "O";
        computerType = "X";

    }
    reset();
    gameOn = true;
    alert("Game created. You are playing as " + playerType);
}