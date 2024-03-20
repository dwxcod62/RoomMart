function receiveWebsocket(callback) {
    console.log("receisocket func")
    var host = "localhost:8080";
    const websocket = new WebSocket("wss://doe-ultimate-wholly.ngrok-free.app/RoomMart/push-noti-websocket");

    // const websocket = new WebSocket("ws://localhost:8080/RoomMart/push-noti-websocket");


    websocket.onmessage = function(message) {

        processMessage(message); };
    websocket.onclose

    function processMessage(message) {

        let mess = JSON.parse(message.data).message;
        let ch = JSON.parse(message.data).chat;
        let rid = JSON.parse(message.data).rid;
        let hid = JSON.parse(message.data).hid;
        console.log("processmess: "+mess,ch,rid,hid);
        callback({
            message: mess,
            chat:ch,
            rid:rid,
            hid:hid,
            duration: 5000,
        });
    };
};
function receiveBoxChatWebsocket(callback) {
    var host = "localhost:8080";
    console.log("receiveBoxChatWebsocket func")
    const websocket = new WebSocket("ws://localhost:8080/RoomMart/show-box-chat");

    websocket.onmessage = function(message) {

        processMessage(message); };
    websocket.onclose

    function processMessage(message) {

        let mess = JSON.parse(message.data).message;
        let ownerId = JSON.parse(message.data).ownerId;
        let renterId = JSON.parse(message.data).renterId;
        let role = JSON.parse(message.data).role;

        let hostelID = JSON.parse(message.data).hostelID;
        let roomID = JSON.parse(message.data).roomID;

        let username = JSON.parse(message.data).username;

        console.log("processmess receiveBoxChatWebsocket: "+ mess,ownerId,renterId,role,hostelID,roomID,username);
        callback({
            message: mess,
            ownerId:ownerId,
            renterId:renterId,
            role:role,
            username: username,
            hostelID:hostelID,
            roomID:roomID,


        });
    };
};
receiveBoxChatWebsocket.disconnectWebSocket = () => {
    websocket && websocket.close();
};
receiveWebsocket.disconnectWebSocket = () => {
    websocket && websocket.close();
};
