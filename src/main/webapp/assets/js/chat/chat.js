const firebaseConfig = {
    apiKey: "AIzaSyB8bS7lu_ouyWgXgl13jewVdHvx8djHAJ8",
    authDomain: "appchat-f5384.firebaseapp.com",
    projectId: "appchat-f5384",
    storageBucket: "appchat-f5384.appspot.com",
    messagingSenderId: "292393765538",
    appId: "1:292393765538:web:5c2467ffbfe6fef3c265fb",
    databaseURL:"https://appchat-f5384-default-rtdb.asia-southeast1.firebasedatabase.app/"
};
firebase.initializeApp(firebaseConfig);

const db = firebase.database();
// var read = false;
const chatForm = document.getElementById("chat");
const readbtn = document.getElementById("read");
const role = prompt("Please Tell Us your role");
const username = prompt("Please Tell Us your Name");
// const userid = prompt("Please Tell Us user id");
//
// const ownerid = prompt("Please Tell Us owner id");

var retrievedValue = sessionStorage.getItem('key');
console.log(retrievedValue); // Output: 'value'

console.log("username : "+username);


console.log("userid : "+renterId);
console.log("ownerid : "+ownerId);



// const username = "thanh";
// const userid = "2";
// const ownerid = "1";

// var firebase_ownerId = db.ref(`chats/${renterId}/`);
// console.log("firebase_ownerId : "+firebase_ownerId);



function roleHandler(){
    console.log("role : "+role);
    if (role == 1){
        readbtn.hidden = false;
    }else{
        chatForm.style.display = "block";
    }
}

window.onload = roleHandler();

// document.getElementById("read-form").addEventListener("submit", showChat);

document.getElementById("message-form").addEventListener("submit", sendMessage);

function showChat() {
    console.log("show chat");
    fetchChat.on("child_added", function (snapshot) {

        fetchChat.child(snapshot.key).update({ read: true });

    });
    //  read = true;
    console.log("hidden readbtn");
    readbtn.hidden = true;
    chatForm.style.display = "block";
}
function sendMessage(e) {
    console.log("send message");
    e.preventDefault();

    // get values to be submitted
    const timestamp = Date.now();
    console.log("timestamp : "+timestamp);
    const messageInput = document.getElementById("message-input");
    const message = messageInput.value;

    // clear the input box
    messageInput.value = "";

    //auto scroll to bottom
    document
        .getElementById("messages")
        .scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });

    var read = false;
    // create db collection and send in the data
    db.ref(`chats/${ownerId}/${renterId}/` + timestamp).set({
        username,
        message,
        read,
    });
}

const fetchChat = db.ref(`chats/${ownerId}/${renterId}/`);

fetchChat.on("child_added", function (snapshot) {

    console.log("value username : "+snapshot.val().username);

    // fetch existing chat messages
    const messages = snapshot.val();
    if(messages.read === false){
        console.log("red - messages.read : "+messages.read);
        readbtn.style.color = "red";

    }
    const message = `<li class=${
        username === messages.username ? "sent" : "receive"
    }><span>${messages.username}: </span>${messages.message}</li>`;
    // append the message on the page
    document.getElementById("messages").innerHTML += message;
});


// let inactivityTimeout; // Biến để lưu trữ timeout
// const inactivityDuration = 5 * 60 * 1000; // 5 phút

// // Thiết lập sự kiện để theo dõi hoạt động của người dùng
// document.addEventListener("mousemove", resetInactivityTimer);

// function resetInactivityTimer() {
//   // Hủy timeout hiện tại nếu có
//   clearTimeout(inactivityTimeout);

//   // Thiết lập một timeout mới
//   inactivityTimeout = setTimeout(handleInactivity, inactivityDuration);
// }

// function handleInactivity() {
//   // Hiển thị thông báo alert
//   alert("Không có hoạt động trong 5 phút!");

//   // Chờ 10 giây trước khi thực hiện xóa dữ liệu không còn cần thiết
//   setTimeout(deleteUnusedData, 10 * 1000);
// }

// function deleteUnusedData() {
//   // Thực hiện xóa dữ liệu không còn cần thiết ở đây
//   console.log("Xóa dữ liệu không còn cần thiết");
//   fetchChat.remove();
// }