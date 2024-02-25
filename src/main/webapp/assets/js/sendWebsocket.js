const websocket = new WebSocket("ws://localhost:8080/RoomMart_war_exploded/push-noti-websocket");
function sendMessage(message) {
    // let i = 0;
    // do {
    //     if (typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN) {
    //         websocket.send(message);
    //     }
    // } while (websocket.readyState != WebSocket.OPEN)
    setTimeout(() => {
            if (typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN) {
                websocket.send(message); //send json

            }
    }, 1000)
}


function sendToWebSocket({ sender, receiver, hostel_receiver_id = null, account_receiver_id = null, messages,chat }){
//sender : hostel_owner,hostel_renter,system
    const message2 = `{"sender":"${sender}","receiver":"${receiver}","hostel_receiver_id":"${hostel_receiver_id}","account_receiver_id":"${account_receiver_id}","message":"${messages}"},"chat":"${chat}"`;
    const message = {
        sender:sender,
        receiver:receiver,
        hostel_receiver_id:hostel_receiver_id,
        account_receiver_id:account_receiver_id,
        message:messages,
        chat:chat};

    console.log(sender, receiver, hostel_receiver_id, account_receiver_id, messages, chat, message);
    sendMessage(JSON.stringify(message));

}