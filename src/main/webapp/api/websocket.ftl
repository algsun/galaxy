<#include "/common/pages/common-tag.ftl">

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>接口说明</title>
    <link rel="stylesheet" href="${basePath()}/assets/bootstrap/2.3.1/css/bootstrap.min.css"/>
</head>
<body>

<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="connect">WebSocket connection:</label>
                    <button id="connect" class="btn btn-default" type="submit">Connect</button>
                    <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                    </button>
                </div>
            </form>
        </div>
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="name">What is your name?</label>
                    <input type="text" id="name" class="form-control" placeholder="Your name here...">
                </div>
                <button id="send" class="btn btn-default" type="submit">Send</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>Greetings</th>
                </tr>
                </thead>
                <tbody id="greetings">
                </tbody>
            </table>
        </div>
    </div>
</div>

<script type="text/javascript" src="${basePath()}/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script type="text/javascript" src="${basePath()}/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script type="application/javascript" src="${basePath()}/assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
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
        var socket = new SockJS('/galaxy/api/data-export');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    }

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $("#connect").click(function () {
            connect();
        });
        $("#disconnect").click(function () {
            disconnect();
        });
        $("#send").click(function () {
            sendName();
        });
    });
</script>
</body>
</html>

<#-- pegdown 实现 -->
<#macro markdown >
    <#local md><#nested/></#local>
${action.markdown2html(md)}
</#macro>
