var stompClient = null;
var socket = null;

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

function connect(endpoint) {
    console.log("connecting to " + endpoint);
    socket = new SockJS(endpoint);
    socket.onopen = function() {
       console.log("SOCKJS endpoint opened!");
        setConnected(true);
       socket.onmessage = function(e) {
        console.log("Message from SockJS:" + e.data);
        console.log("As OBJECT:" + JSON.parse(e.data));
        var obj = JSON.parse(e.data);
        console.log("As OBJECT2:" + obj);
        showGreeting(obj.name);
       }

       var msg = "{\"username\":\"Peter\",\"password\":\"slemmigtorsk\",\"hostname\":\"localhost\"}";
       console.log("SENDING:" + msg);
       socket.send(msg);
    };
}

function query() {
    fetch("/ownorders").then();
}

function connectStomp(endpoint, subscription_endpoint) {
    console.log("connecting to .." + endpoint);
    var socket = new SockJS(endpoint);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe(subscription_endpoint, function (greeting) {
            console.log("subscribe callback got: <" + greeting + "> <" + greeting.body + ">");
            showGreeting(JSON.parse(greeting.body).name);
        });
    });
}

function disconnect() {
    socket.close();
    socket = null;
    setConnected(false);
    console.log("Disconnected");
}

function disconnectStomp() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function sendName() {
    //stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    var message = JSON.stringify({'name': $("#name").val()});
    console.log("Sending:" + message);
    socket.send(message);
}

function showGreeting(message) {

    console.log("showGreeting:" + message);
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect("http://localhost:8080/sockjs"); });
    $( "#query" ).click(function() { query(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
