function receiveWebsocket(callback) {
    console.log("receisocket func")
    const websocket = new WebSocket("ws://localhost:8080/RoomMart/push-noti-websocket");

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

receiveWebsocket.disconnectWebSocket = () => {
    websocket && websocket.close();
};
