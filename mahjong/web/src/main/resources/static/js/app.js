var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        onConnectStatusChanged(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            var message = JSON.parse(greeting.body);
            showGreeting(message.name + ":" + message.message);
        });
        stompClient.subscribe('/topic/game', function (gameMessage) {
            var message = JSON.parse(gameMessage.body);
            showGameNotice(message.players[1]);
            showGameMessage(message.gameStatus);
        });
        
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    onConnectStatusChanged(false);
}

function sendMessage() {
    stompClient.send("/app/hello", {}, JSON.stringify({'@class': 'jp.btsol.mahjong.model.MahjongMessage', 'name': $("#name").val(),'message': $("#message").val()}));
    $("#message").val('');
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    //$("form").on('submit', function (e) {
    //    e.preventDefault();
    //});
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});

function onConnectStatusChanged(status){
}

setTimeout("connect()", 3000);