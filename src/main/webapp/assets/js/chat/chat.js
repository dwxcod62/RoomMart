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
// console.log(db);
const chatForm = document.getElementById("messages");
const chatInput = document.getElementById("chat-id-1-form");
const chatHeader = document.getElementById("chatHeader");



const readbtn = document.getElementById("read");
const sidebarList = document.getElementById("sidebarList");
const sidebarProfile = document.getElementById("chat-1-user-profile");




// chat-1-user-profile
function showProfileSidebar(){
    sidebarProfile.style.display="block";
};
function hideProfileSidebar(){
    sidebarProfile.style.display="none";
};
function showSidebar(){
    sidebarList.style.display="block";
};

function hideSidebar(){
    sidebarList.style.display="none";
};
function roleHandler() {
    console.log("role : " + role);
    if (role == 1) {
         // username = "admin";
        showChat();
        sidebarList.style.display="block";
        chatInput.hidden=true;
        showChat();
        if(renterId == "null"){
            chatHeader.style.display="none";
        }

    } else {
        // username = "thanh";
        readbtn.hidden = true;
        chatForm.hidden = false;
        chatInput.hidden = false;

    }
    console.log("Acc name: "+username);
}

window.onload = roleHandler();
console.log("chat js--------------------------------------------------------")

console.log("userid : " + renterId);
console.log("ownerid : " + ownerId);
console.log("hostelID:"+hostelID);
console.log("roomId:"+roomID);
// document.getElementById("read-form").addEventListener("submit", showChat);

document
    .getElementById("chat-id-1-form")
    .addEventListener("submit", sendMessage);

function showChat() {
    const fetchChat2 = db.ref(`chats/${ownerId}/${renterId}/`);
    console.log("show chat");
    fetchChat2.on("child_added", function (snapshot) {
        fetchChat2.child(snapshot.key).update({ read: true });
    });
    //  read = true;
    var roleP;
    console.log("check role handle")
    if (role==1){
        roleP="Chủ trọ"
        readbtn.hidden = true;
        chatForm.hidden = false;
        chatInput.hidden = false;

    }
    if (role==2){
        roleP="Nhân viên"
    }
    if (role==3){
        roleP="Người dùng"
    }





    if (document.getElementById("messages") != null) {
        document.getElementById("messages").scrollIntoView(true);

    };

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
    // sendToWebSocket("hostel_owner", "hostel_renter", 23, 23, "messages","chat");

    if (accId==renterId){
        console.log("Send notify to owner");
        sendToWebSocket("hostel_renter", "hostel_owner", null, ownerId, null,message,null,null);
    }
    if (accId==ownerId){
        console.log("Send open box chat to renter");
        sendToWebSocket2("hostel_owner", "hostel_renter", null, renterId, null,null,null,null,renterName,"2",renterId,ownerId);
        console.log("Send notify to renter");
        sendToWebSocket( "hostel_owner","hostel_renter", null, renterId, null,message,roomID,hostelID);
    }

    // clear the input box
    messageInput.value = "";

    //auto scroll to bottom
    document
        .getElementById("messages")
        .scrollIntoView(true);

    var read = false;
    // create db collection and send in the data
    db.ref(`chats/${ownerId}/${renterId}/` + timestamp).set({
        username,
        message,
        formattedDate,
        read,
        hostelID,
        roomID,
        role,
    });
}

const fetchUserId = db.ref(`chats/${ownerId}/`);
const fetchChat = db.ref(`chats/${ownerId}/${renterId}/`);

if(role==1){
    fetchUserId.on("child_added", function (snapshot) {
        console.log("user check : " + snapshot.key);
        const fetchChat2 = db.ref(`chats/${ownerId}/${snapshot.key}/`);
        var count = 0;
        var renterName ="";
        var newMess ="";

        //   const userList = `<li class="user-list"><a href="#adudu">${snapshot.key}</a></li>`;
        fetchChat2.on("child_added", function (snapshot2) {
            const messages = snapshot2.val();
            if (messages.read === false) {
                count++;
                console.log("red - messages.read : " + messages.read);
            }
            if (username != messages.username){
                renterName = messages.username;
                newMess = messages.message;

            }


        });
        let userList2 = `<a class="text-reset nav-link p-0 mb-6" href="chat?renterId=${snapshot.key}">
                                            <div class="card card-active-listener">
                                                <div class="card-body">

                                                    <div class="media">
                                                        
                                                        
                                                        <div class="avatar mr-5">
                                                            <img class="avatar-img" src="https://animalcharityevaluators.org/wp-content/uploads/2016/09/animals-now-logo-icon-only.png" alt="${snapshot.key}">
                                                        </div>
                                                        
                                                        <div class="media-body overflow-hidden">
                                                            <div class="d-flex align-items-center mb-1">
                                                                <h6 class="text-truncate mb-0 mr-auto">${renterName} (User)</h6>
                                                                
                                                            </div>
                                                            <div class="text-truncate">${newMess}</div>
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
    console.log("messages: "+messages);
    // var link = document.getElementById("link-room-detail");
    // var stringUrl = "roomDetail?hostelId=${"+messages.hostelID+"}&rid=${"+messages.roomID+"}";
    // console.log("Room URL: "+stringUrl);
    // link.href=stringUrl;
    // let message2 =
    //     username === messages.username
    //         ? ` <div class="message message-right">
    //                                 <!-- Avatar -->
    //                                 <div class="avatar avatar-sm ml-4 ml-lg-5 d-none d-lg-block">
    //                                     <img class="avatar-img" src="https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/sheep_mutton_animal_avatar-512.png" alt="">
    //                                 </div>
    //
    //                                 <!-- Message: body -->
    //                                 <div class="message-body">
    //
    //                                     <!-- Message: row -->
    //                                     <div class="message-row">
    //                                         <div class="d-flex align-items-center justify-content-end">
    //
    //                                             <!-- Message: content -->
    //                                             <div class="message-content bg-primary text-white">
    //                                                 <div>${messages.message}</div>
    //
    //                                                 <div class="mt-1">
    //                                                     <small class="opacity-65">${messages.formattedDate}</small>
    //                                                 </div>
    //
    //
    //
    //                                             </div>
    //                                             <!-- Message: content -->
    //
    //                                         </div>
    //                                     </div>
    //                                     <!-- Message: row -->
    //
    //                                 </div>
    //                                 <!-- Message: body -->
    //                             </div>`
    //         : ` <div class="message">
    //                                 <!-- Avatar -->
    //                                 <a class="avatar avatar-sm mr-4 mr-lg-5" href="#" onclick="showProfileSidebar()">
    //                                     <img class="avatar-img" src="https://animalcharityevaluators.org/wp-content/uploads/2016/09/animals-now-logo-icon-only.png" alt="">
    //                                 </a>
    //
    //                                 <!-- Message: body -->
    //                                 <div class="message-body">
    //
    //                                     <!-- Message: row -->
    //                                     <div class="message-row">
    //                                         <div class="d-flex align-items-center">
    //
    //                                             <!-- Message: content -->
    //                                             <div class="message-content bg-light">
    //                                                 <div>${messages.message}</div>
    //
    //                                                 <div class="mt-1">
    //                                                     <small class="opacity-65">${messages.formattedDate}</small>
    //                                                 </div>
    //
    //                                                 <a class="nav-link" href="roomDetail?hostelId=${messages.hostelID}&rid=${messages.roomID}">
    //                                         <i class="fe-chevrons-right"></i>
    //                                        Xem Phòng
    //                                     </a>
    //
    //
    //                                             </div>
    //                                             <!-- Message: content -->
    //
    //                                         </div>
    //                                     </div>
    //                                     <!-- Message: row -->
    //
    //                                 </div>
    //                                 <!-- Message: body -->
    //                             </div>`;
    let message2;

    if (username === messages.username) {
        message2 = '<div class="message message-right">' +
            '<!-- Avatar -->' +
            '<div class="avatar avatar-sm ml-4 ml-lg-5 d-none d-lg-block">' +
            '<img class="avatar-img" src="https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/sheep_mutton_animal_avatar-512.png" alt="">' +
            '</div>' +
            '<!-- Message: body -->' +
            '<div class="message-body">' +
            '<!-- Message: row -->' +
            '<div class="message-row">' +
            '<div class="d-flex align-items-center justify-content-end">' +
            '<!-- Message: content -->' +
            '<div class="message-content bg-primary text-white">' +
            '<div>' + messages.message + '</div>' +
            '<div class="mt-1">' +
            '<small class="opacity-65">' + messages.formattedDate + '</small>' +
            '</div>' +
            '</div>' +
            '<!-- Message: content -->' +
            '</div>' +
            '</div>' +
            '<!-- Message: row -->' +
            '</div>' +
            '<!-- Message: body -->' +
            '</div>';
    } else {
        message2 = '<div class="message">' +
            '<!-- Avatar -->' +
            '<a class="avatar avatar-sm mr-4 mr-lg-5" href="#" onclick="showProfileSidebar()">' +
            '<img class="avatar-img" src="https://animalcharityevaluators.org/wp-content/uploads/2016/09/animals-now-logo-icon-only.png" alt="">' +
            '</a>' +
            '<!-- Message: body -->' +
            '<div class="message-body">' +
            '<!-- Message: row -->' +
            '<div class="message-row">' +
            '<div class="d-flex align-items-center">' +
            '<!-- Message: content -->' +
            '<div class="message-content bg-light">' +
            '<div>' + messages.message + '</div>' +
            '<div class="mt-1">' +
            '<small class="opacity-65">' + messages.formattedDate + '</small>' +
            '</div>' +
            (messages.hostelID != "null" ? '<a class="nav-link" href="ownerRoomDetail?hostelID=' + messages.hostelID + '&roomID=' + messages.roomID + '">' +
                '<i class="fe-chevrons-right"></i>' +
                'Xem Phòng' +
                '</a>' : '') +
            '</div>' +
            '<!-- Message: content -->' +
            '</div>' +
            '</div>' +
            '<!-- Message: row -->' +
            '</div>' +
            '<!-- Message: body -->' +
            '</div>';
    }

    let message3 =
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
                                    <a class="avatar avatar-sm mr-4 mr-lg-5" href="#" onclick="showProfileSidebar()">
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

if(messages.role==='2'){
    console.log("messrole: "+messages.role)
    document.getElementById("messages").innerHTML += message2;}
else {
    console.log("messrole else: "+messages.role)
    document.getElementById("messages").innerHTML += message3;
}




    if (document
        .getElementById("messages") != null)
    document
        .getElementById("messages")
        .scrollIntoView(false);
});
