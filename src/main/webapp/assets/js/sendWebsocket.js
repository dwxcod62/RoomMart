

const websocket = new WebSocket("ws://localhost:8080/RoomMart/push-noti-websocket");
const websocket2 = new WebSocket("ws://localhost:8080/RoomMart/show-box-chat");
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
function sendMessage2(message) {
    console.log("-> sendMessage2");
    setTimeout(() => {
        if (typeof websocket2 != 'undefined' && websocket2.readyState == websocket2.OPEN) {

            websocket2.send(message); //send json

        }
    }, 1000)
}

function sendToWebSocket( sender, receiver, hostel_receiver_id = null, account_receiver_id = null, messages=null,chat=null,hostelId=null,roomId=null ){
    console.log(sender,receiver,account_receiver_id,messages,chat);
//sender : hostel_owner,hostel_renter,system
    const message2 = `{"sender":"${sender}","receiver":"${receiver}","hostel_receiver_id":"${hostel_receiver_id}","account_receiver_id":"${account_receiver_id}","message":"${messages}","chat":"${chat}","hid":"${hostelId}","rid":"${roomId}"}`;
    const message = {
        sender:sender,
        receiver:receiver,
        hostel_receiver_id:hostel_receiver_id,
        account_receiver_id:account_receiver_id,
        message:messages,
        chat:chat};

    sendMessage(message2);

}

function sendToWebSocket2( sender, receiver, hostel_receiver_id = null, account_receiver_id = null, messages=null,chat=null,hostelID=null,roomID=null ,username,role,renterId,ownerId){
    console.log("sendToWebSocket2: "+sender,receiver,account_receiver_id,messages,chat,hostelID,roomID,username,role,renterId,ownerId);
//sender : hostel_owner,hostel_renter,system
    const message2 = `{"sender":"${sender}","receiver":"${receiver}","hostel_receiver_id":"${hostel_receiver_id}","account_receiver_id":"${account_receiver_id}","message":"${messages}","chat":"${chat}","hostelID":"${hostelID}","roomID":"${roomID}","username":"${username}","role":"${role}","renterId":"${renterId}","ownerId":"${ownerId}"}`;
    const message = {
        sender:sender,
        receiver:receiver,
        hostel_receiver_id:hostel_receiver_id,
        account_receiver_id:account_receiver_id,
        message:messages,
        chat:chat};

    sendMessage2(message2);

}