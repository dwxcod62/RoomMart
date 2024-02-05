const firebaseConfig = {
    apiKey: "AIzaSyB8bS7lu_ouyWgXgl13jewVdHvx8djHAJ8",
    authDomain: "appchat-f5384.firebaseapp.com",
    projectId: "appchat-f5384",
    storageBucket: "appchat-f5384.appspot.com",
    messagingSenderId: "292393765538",
    appId: "1:292393765538:web:5c2467ffbfe6fef3c265fb",
    databaseURL:
        "https://appchat-f5384-default-rtdb.asia-southeast1.firebasedatabase.app/",
};
firebase.initializeApp(firebaseConfig);

const db = firebase.database();
// var read = false;
const chatForm = document.getElementById("messages");
const chatInput = document.getElementById("chat-id-1-form")

// const readbtn = document.getElementById("read");
// const role = prompt("Please Tell Us your role");
// const username = prompt("Please Tell Us your Name");
// const role = 2;

var username;

// const userid = prompt("Please Tell Us user id");

// const ownerid = prompt("Please Tell Us owner id");
console.log("username : " + username);
console.log("userid : " + renterId);
console.log("ownerid : " + ownerId);
const readbtn = document.getElementById("read");
const sidebarList = document.getElementById("sidebarList");
// const username = "thanh";
// const userid = "2";
// const ownerid = "1";

// var firebase_ownerId = db.ref(`chats/${ownerid}/`);
// console.log("firebase_ownerId : "+firebase_ownerId);
function showSidebar(){
    sidebarList.style.display="block";
};

function hideSidebar(){
    sidebarList.style.display="none";
};
function roleHandler() {
    console.log("role : " + role);
    if (role == 1) {
        username = "admin";
        sidebarList.style.display="block";

    } else {
        username = "thanh";
        readbtn.hidden = true;
        chatForm.hidden = false;
        chatInput.hidden = false;

    }
    console.log("Username: "+username)
}

window.onload = roleHandler();

// document.getElementById("read-form").addEventListener("submit", showChat);

document
    .getElementById("chat-id-1-form")
    .addEventListener("submit", sendMessage);

function showChat() {
    console.log("show chat");
    fetchChat.on("child_added", function (snapshot) {
        fetchChat.child(snapshot.key).update({ read: true });
    });
    //  read = true;
    readbtn.hidden = true;
    chatForm.hidden = false;
    chatInput.hidden = false;
}
function sendMessage(e) {
    console.log("send message");
    e.preventDefault();

    // get values to be submitted
    const timestamp = Date.now();
    const currentDate = new Date();

    // Get the day, month, and year
    const day = currentDate.getDate();
    const month = currentDate.getMonth() + 1; // Months are zero-based, so add 1
    const year = currentDate.getFullYear();

    // Format the date as "dd/mm/yyyy"
    const formattedDate = `${day}/${month}/${year}`;
    console.log("timestamp : " + timestamp);
    const messageInput = document.getElementById("chat-id-1-input");
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
        formattedDate,
        read,
    });
}

const fetchUserId = db.ref(`chats/${ownerId}/`);
const fetchChat = db.ref(`chats/${ownerId}/${renterId}/`);

if(role==1){
    fetchUserId.on("child_added", function (snapshot) {
        console.log("user check : " + snapshot.key);
        const fetchChat2 = db.ref(`chats/${ownerId}/${snapshot.key}/`);
        var count = 0;
        //   const userList = `<li class="user-list"><a href="#adudu">${snapshot.key}</a></li>`;
        fetchChat2.on("child_added", function (snapshot2) {
            const messages = snapshot2.val();
            if (messages.read === false) {
                count++;
                console.log("red - messages.read : " + messages.read);
            }
        });
        const userList2 = `<a class="text-reset nav-link p-0 mb-6" href="chat?renterId=${snapshot.key}">
                                            <div class="card card-active-listener">
                                                <div class="card-body">

                                                    <div class="media">
                                                        
                                                        
                                                        <div class="avatar mr-5">
                                                            <img class="avatar-img" src="https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/sheep_mutton_animal_avatar-512.png" alt="${snapshot.key}">
                                                        </div>
                                                        
                                                        <div class="media-body overflow-hidden">
                                                            <div class="d-flex align-items-center mb-1">
                                                                <h6 class="text-truncate mb-0 mr-auto">User ${snapshot.key}</h6>
                                                                
                                                            </div>
                                                            <div class="text-truncate">Anna Bridges: Hey, Maher! How are you? The weather is great isn't it?</div>
                                                        </div>
                                                    </div>

                                                </div>

                                                <div class="badge badge-circle badge-primary badge-border-light badge-top-right">
                                                        <span>${count}</span>
                                                    </div>
                                                
                                                
                                            </div>
                                        </a>`;
        // append the message on the page
        document.getElementById("user-list").innerHTML += userList2;
    });

}


fetchChat.on("child_added", function (snapshot) {
    console.log("parent : " + snapshot.ref.parent.key);

    // fetch existing chat messages
    const messages = snapshot.val();

    const message2 =
        username === messages.username
            ? ` <div class="message message-right">
                                    <!-- Avatar -->
                                    <div class="avatar avatar-sm ml-4 ml-lg-5 d-none d-lg-block">
                                        <img class="avatar-img" src="https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/sheep_mutton_animal_avatar-512.png" alt="">
                                    </div>

                                    <!-- Message: body -->
                                    <div class="message-body">

                                        <!-- Message: row -->
                                        <div class="message-row">
                                            <div class="d-flex align-items-center justify-content-end">

                                                <!-- Message: content -->
                                                <div class="message-content bg-primary text-white">
                                                    <div>${messages.message}</div>

                                                    <div class="mt-1">
                                                        <small class="opacity-65">${messages.formattedDate}</small>
                                                    </div>
                                                </div>
                                                <!-- Message: content -->

                                            </div>
                                        </div>
                                        <!-- Message: row -->

                                    </div>
                                    <!-- Message: body -->
                                </div>`
            : ` <div class="message">
                                    <!-- Avatar -->
                                    <a class="avatar avatar-sm mr-4 mr-lg-5" href="#" data-chat-sidebar-toggle="#chat-1-user-profile">
                                        <img class="avatar-img" src="https://animalcharityevaluators.org/wp-content/uploads/2016/09/animals-now-logo-icon-only.png" alt="">
                                    </a>

                                    <!-- Message: body -->
                                    <div class="message-body">

                                        <!-- Message: row -->
                                        <div class="message-row">
                                            <div class="d-flex align-items-center">

                                                <!-- Message: content -->
                                                <div class="message-content bg-light">
                                                    <div>${messages.message}</div>

                                                    <div class="mt-1">
                                                        <small class="opacity-65">${messages.formattedDate}</small>
                                                    </div>
                                                </div>
                                                <!-- Message: content -->

                                            </div>
                                        </div>
                                        <!-- Message: row -->

                                    </div>
                                    <!-- Message: body -->
                                </div>`;

    // append the message on the page
    document.getElementById("messages").innerHTML += message2;
    if (document
        .getElementById("messages") != null)
    document
        .getElementById("messages")
        .scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });
});
