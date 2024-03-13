function alertPushNoti({ message = "",chat="",rid="",hid="", duration = "" }) {
    console.log("mess alert push noti: "+message);
    console.log("chat alert push noti: "+chat);
    console.log("rid alert push noti: "+rid);
    console.log("hid alert push noti: "+hid);
    const mainNoti = document.getElementById("push-noti");


    // const notify = (message != null ? message : chat);
    const notify = message;

    if (mainNoti) {
        const noti = document.createElement("div");

        // Auto remove noti
        const autoRemoveId = setTimeout(function () {
            mainNoti.removeChild(noti);
        }, duration + 500);

        // Remove noti when clicked
        noti.onclick = function (e) {
            if (e.target.closest(".push-noti__close")) {
                mainNoti.removeChild(noti);
                clearTimeout(autoRemoveId);
            }
        };

        const delay = (duration / 1000).toFixed(2);

        noti.classList.add("push-noti");
        noti.style.animation = `slideInRight ease .3s, fadeOut linear 2s ${delay}s forwards`;
        var n1 = `
            <div class="push-noti__wrapper">
              <h5 class="push-noti__title">Thông báo mới</h5>
              <div class="push-noti__close">
                <i class="fa-solid fa-xmark"></i>
              </div>
            </div>
            <div class="push-noti__wrapper">
              <div class="push-noti__icon">
                <i class="fa-solid fa-circle-info"></i>
              </div>
              <div style="overflow: hidden" class="push-noti__body">


              
                <p style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden" class="push-noti__msg">${notify}</p>
                
                <p class="push-noti__time">vài giây trước</p>
              </div>
            </div>
            <div class="push-noti__dot"></div>
            <audio autoplay src="data:audio/mpeg;base64,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FGFaMubBYRRgrgfmCKAAYboNBiLl7G8aTyYc4JxmhKYiGBgagu12el7XT/GVesumZTKce/cXwBhFmKSly8cKuG0DRdTf///////////+kEAKC6YlQBZgCgUmAsDYYUKy5qwjrGEOE6YfAJRh7AdmHOkGc5aHhiJhKmCMA8YBIA5cRHpYRy3rZcYw4LC5qLvJJ//gzaE9y7c5/df3HL7m9/+73//e8//y////wTUApbOVAkwGKTCxuMJ9aM1YxzjCDCbMPQE4w+ASTDrR7OdNDYxHQhzBIAaMA8BUs4DgClkYszWMYwoLTDoxTWJRa0MAaGL3H1K746doIzG9bm9n////////////j2AMAIEEWFgEYP/7kkS/jZL5GEML3DLAXaL4lXtlWQudswwPAF1BcY1hhc8seEJgAg2GB8wmZYw6BKAKYXgCxirBkmK8macrSDRiJBLnTfGoMmNDAAmwaWD6ElA70BwtNHtFyP1HQ6prZzjtutzdzJR1ac92f////////////k1gAACAWkQggyAmCQSjAoStMhgWIKAAmEYAUYfwSJiCHVm5YYgYbgNZ5xGsOAgjCFi0tIsKgAOOQ40iZrL9BbiQmrs5x+pndzJIzTdjVb9H////////////kjA1wjcwb4A2MBiAIzA0QLowsQuLNWSDGjCEgLwwpUCDMIzAADDJClI3hAofMNWBWj91M3wyM/HTFlIIIwMGCwABgQgKGhGGqLAbDQAEWvLow1VlRbRV0OTdND0Wyzs18+U2UMRezhUmM7l/0Js////////////7QWAACccNf+08MLjJwbNZqYwnIovNLkCvjBzAIwwjkB5CA9MwtgjqNpiIvTC0ASA0FE0xaBYwjAcCh4YBgKuRjpg2BB8+WoGCZajUoSzikllPURUX9IJXPP9Fr9f/+5JEw43C1hrDC9qqkFlDSIJ7NVIPEG78D+/KQhkP383P9Fi9fpcJdqUQ/eqVKk/hbz/9X+2srR4sf/cKsQ/oWyh/pZNV76//+1N///6gZT9hjOCD8FSg4uVzDng9Y2+IFLMKeAgTDTwGUxH0KoMQHQYDX4ziMw0cJaMSoOEwnQnDBEBdEgazAQAZLvBUA0QATmjgNwwuYfmNPPY1lKAqAwziN08NNli13Clr1Jm1Ttke+mnaeafPC3ykr83jP1DEBf///////////yaQBh2y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CEAYaAhLbMFjcWZkYOgdLaDUQGk3Ehm1bIVv9E0EyNUDmFXMD/////////////pqMGdATjBWwH8wCkESMAJCmzBtnpQ0AwmzMB3BLDE2AB8x//uSRO6J09lsvgPgF1CaBAexf35SiehrEsz4o6FbjSFFryBwp4IDMBtYqzZaVqwwi8NnMBYYwwHQzxADQBAUzAOA4MBEBMFATjIGZnQprK/XYi3CER7MpnLwUAAWu4jKXSWCe6X25i78TvSGFKRdCvG45Bs5VtHhToREwhBCIaxzmsccpymznNZDZ01kdXm3daNebT/////////////////4xAhEgaERMm6RoKQCwshgEQCag3UAj4PQQlGEI4EaEiXZLlAnpIpLUp3RdSSqq1qpOlrtv0ur/1EgBAyJLN1ATH0o0jZMETJ7DI8wxcwLcEMMDAARTBngQMwpIUBNJmD/jCVgLE12PDJwlMWgQAA8WBy/JKlCditY8CPjGoF1v9N2jF2vrKRcpv/fc900OfvX26zyv////////////hN9IsGGFKBvjAcvRuC6XdavPPQCKHWGRWu9eYP0sY71//////////////WH6hgAACSBosr4yxEwox0jXnB3MEkCowbwDTC3AmMEIwA0ciwzBoBKMAsB0votNNCG4xLJEYRYM//7kmTPj/UlbT0D/lLwNK2IIEgC6kyMaQQN/4LAqoZhQJwkYDQxzoJNwmszr8zOoSFasot/////////////1jCAAFYSRJ9GeMmGCQcbXYR5gngYGEGAqYaYGhgmG3GowZyYPwLhgKgSkIACX6CSCYhFHvMKUHpp6EMA3UJlQzr9FdRIVWqj23f////////////SKmF6BsREYiQmrKhi+o+ni2NUYZoVphDgaGMmG4ZCajh8GoamOiFQYRwK5gegPGA0AWPAPoUvdTJiGLEGO/I87QPuKJMUrZSAZZhDP2ZLnG8/t3zn////////////JGAOGkYMgERgJgPmCsCoYu6SZ4rjOGGmFWYPYEhjLhsGQKowfEKI5jbhPGEgCKYHIExgPABg4CNH1q8PFwDFiDDeWbkOUE1aGm7DuX6wjuPMP/XMrdBR3sfu4f+fO9/n////+IUCdzADB6MNoBwwEQTzA4DMMN2Zc3PTHjDBD3MXAIYxbgeTEnaDPPViwxcQzTBqBEMAMBkuICgAkEjD2tsn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4HVNh6N7ZVrI1bUACwD7gU42nwHgH3CZMBl6gwgSWzA3BaMNQAkxMAYzAySdMiVDYwAAlzABAgGADSEAgUADj0N23hMAcGh2/r2YCs2b+26y+g3d5hlll3v9y5J7uW9T///sIARhgGgEDAEZgOgkGDMFeYu8XR4AlHmHuGsYMwI5hnBPmO8UOfLQxxjdAomEaAEYGAAoKAuDABUsm0jichgVBItM1+2Qzuv0qpR8/62P////6jsL//3Iv//ngNA2uYAYABIAuYCYFhgjBBmI+1ocnY85hchQGB4BuYSgO5i2D5nYgJMYpAIpgzgAAYC0iAaWor5plM1wwGgaXUptTr41LlNnAFrLvY99vDDef4c+RXcdbns//v8//////9JEUxrVAoFcYA8AZmBogFhgzgB2YxKLJHiEgNRhr4EgYYmAlmKig3xgHCbwaOEjomDABRBgm6iJUCIngwbEgNLUFoB4bHFfMCgHHZAsQCAZ8G+hMkMBghLlnD5WWYU0vx1a3jViDc0au75BqzM8erRUlRUlU69K//uSRPGP8t5tPAPAbuBX7YfAeALcS2G08g8BW4GGtl7B4Deov////9f/////////////////+SADXCeiZC3X7TPewFDuqlBUuWmgwIyF6FNm6um8D/xDU3U3nNSuZvSzDHkan87lnPDv8/X4TV7n/+f//QqTgyKTAewQIwPoBUMDvBbzCEAyIxy+E5PixMRjEVRA8wHEHaMWFDJDIhz3I+5MqBMerB+DrBezSkrjOQYTJ0BDE4FwcE5UCMRAacFUOChIYarXbMCwJT3WCUjLxUR3Na9DhECYVANH6NY2r1vDbgBgUFmZnLpdQTWq7WTUrv/3////////////9v//6//////6xzwxWAJoWLEHdwEqHVVIF7z2KGknpDhy+6lBc9Yik1YkiHql0DvrORHGkgeIT/JDjX5EO7oI/jlzvM9VpRPf//rv/5THWI4HAfUwAoBHMBeAbDAEwQEwDYIEMC/XnTHHx54wQkIBMK2A+jDFgVcwS8ydNDRL4DBuQbcwIACyMAZATwsAhL3MAEACguABv+OACZgioH0RAA8MULgpm//7kmT4D/RibTkD/GrwOU2XgGAK3BLhtt4P9kuBADadQZBDcFPpratDZ6kNZJTyqzr+//0i2ZTv//////ltSYihiTjbGXFnjGgUGB6I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ezz2nQZbqahTq9OhX/q/3q9f//bf/////1///////////r/uAYARxdHIkoAAHQ6GgAA4eHAcn///3//+/+y06mTTUnQpuggtBaakGTN0DdRcRL58uFwzIgd0D5FQMnxbCMC9ykwwGQBRMC1AsDA8AG8woUGmMe+cBT6PBLYxUwFsMNfAbzAvgZExjQR0PKmKYTGBgNQ+iVDqg2NagIeLhicAGGhIDhqNAE67MzGQkT9T5ZUYOBDS2JRFpJhQYkQKlUdfmajNJGr8pnZqXXabOl5TWqv/+5Jk/4xlOW21A/1q8C3ttbEAFa4P/bTcT+2rQOY2mfRQTrxm5SqLzoskktJaLJOiipFSVFkklpUaTrZTUrVOvZSX+v//////////f/////6jo+ADXQ5BOAsRYEywHg0AZokB2HwgOIFJJBN6/////11b0aTrZaNKiySSklosk5iarLspEuNErAYbAAdQAgOBEwBUtAACANBYCYwNAKjB5BbMdYqA/AB/zFUBMAxSZizhjGFytkZpKfJhFhgGMVmJJGCHgUYjK6ErcI1UJgedPlL/wwuIdB5jGZzzz39k/zzz3Pv9v///+////////////////////kw4ooFLA9xNwEoUP//////9a9daf//9ugpBBBNNNNNNNNNBD4PBfMyBTEFNRTMuMTAwVVVVVVVVVVULAVBgGwA2YDkEBGB6iLpif1UQdk8gumFOAuxghwK2YKqFxGOpiZx9S5EoY26AkHCALGs4uGXAomBYHGIgSDxRlgIzAcETJVyzDQFkALJYZf1yaaeipQG5MKE/h3X/pt+v3/X////3/t//t/////9v//uSZPaM5TRttQP8ovA3DaYAUBS+DRW06G9o6wCXNp0I0Da+//tT/////6KNzwGiggSAEIAqB5RgAZL7YGriQAyaAWA4thEycME1f///6/W1J31KUkj/W9FFFFE1WQItkcT4noRwBUGAadxQHQyuBjNfgaaC5LAApy7AYuAguYaiGXWJhVyPGg2VSYWYMhhwAomJcDeYLCVpgEoVGB4Eud+QouKCDIKm0+xNMswsFjzcvqUmNJyndGDsBOXAg0LHAwTIF1jhr3YgLruyBNC/kEo/l1f/fb///7EJKAMLDqxAAIG4Ax4oDGJQNUzBZwLgRp/////hzfdYfn3PusN59w5hhvPuHK9vdPlSXq8v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">
            </audio>`;

       
            noti.innerHTML =n1;

        mainNoti.appendChild(noti);
    }
}
function showBoxChat({ message = "",ownerId="",renterId="",role="",
                         username = "",hostelID="",roomID="" }) {
    console.log("mess: "+message);
    console.log("ownerId: "+ownerId);
    console.log("renterId: "+renterId);
    console.log("role: "+role);
    console.log("username: "+username);
    console.log("hostelID: "+hostelID);
    console.log("roomID: "+roomID);
    const accId = renterId;
    toggleChatBox();
    if(ownerId!=0 & renterId!=0) {
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
        console.log(db);
        const chatForm = document.getElementById("messages");
        const chatInput = document.getElementById("chat-id-1-form");
        const chatHeader = document.getElementById("chatHeader");


        const readbtn = document.getElementById("read");
        const sidebarList = document.getElementById("sidebarList");
        const sidebarProfile = document.getElementById("chat-1-user-profile");


// chat-1-user-profile
        function showProfileSidebar() {
            sidebarProfile.style.display = "block";
        };

        function hideProfileSidebar() {
            sidebarProfile.style.display = "none";
        };

        function showSidebar() {
            sidebarList.style.display = "block";
        };

        function hideSidebar() {
            sidebarList.style.display = "none";
        };

        function roleHandler() {
            console.log("role : " + role);
            if (role == 1) {
                // username = "admin";
                showChat();
                sidebarList.style.display = "block";
                chatInput.hidden = true;
                showChat();
                if (renterId == "null") {
                    chatHeader.style.display = "none";
                }

            } else {
                // username = "thanh";
                readbtn.hidden = true;
                chatForm.hidden = false;
                chatInput.hidden = false;

            }
            console.log("Acc name: " + username);
        }

        window.onload = roleHandler();
        console.log("chat js--------------------------------------------------------")

        console.log("userid : " + renterId);
        console.log("ownerid : " + ownerId);
        console.log("hostelID:" + hostelID);
        console.log("roomId:" + roomID);
// document.getElementById("read-form").addEventListener("submit", showChat);

        document
            .getElementById("chat-id-1-form")
            .addEventListener("submit", sendMessage);

        function showChat() {
            const fetchChat2 = db.ref(`chats/${ownerId}/${renterId}/`);
            console.log("show chat");
            fetchChat2.on("child_added", function (snapshot) {
                fetchChat2.child(snapshot.key).update({read: true});
            });
            //  read = true;
            var roleP;
            console.log("check role handle")
            if (role == 1) {
                roleP = "Chủ trọ"
                readbtn.hidden = true;
                chatForm.hidden = false;
                chatInput.hidden = false;

            }
            if (role == 2) {
                roleP = "Nhân viên"
            }
            if (role == 3) {
                roleP = "Người dùng"
            }


            if (document.getElementById("messages") != null) {
                document
                    .getElementById("end-of-chat")
                    .scrollIntoView(true);
                // document
                //     .getElementById("chatContainer")
                //     .scrollIntoView({behavior: "smooth", block: "end", inline: "nearest"});
                document
                    .getElementById("chat-footer")
                    .scrollIntoView(true);

            }
            ;

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

            if (accId == renterId) {
                console.log("Send notify to owner");
                sendToWebSocket("hostel_renter", "hostel_owner", null, ownerId, null, message, null, null);
            }
            if (accId == ownerId) {
                console.log("Send notify to renter");
                sendToWebSocket2("hostel_owner", "hostel_renter", null, renterId, null,message,1,1,username,role,renterId,ownerId);
                sendToWebSocket("hostel_owner", "hostel_renter", null, renterId, null, message, roomID, hostelID);
            }

            // clear the input box
            messageInput.value = "";

            //auto scroll to bottom
            document
                .getElementById("end-of-chat")
                .scrollIntoView(true);
            // document
            //     .getElementById("chatContainer")
            //     .scrollIntoView({behavior: "smooth", block: "end", inline: "nearest"});
            document
                .getElementById("chat-footer")
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

        if (role == 1) {
            fetchUserId.on("child_added", function (snapshot) {
                console.log("user check : " + snapshot.key);
                const fetchChat2 = db.ref(`chats/${ownerId}/${snapshot.key}/`);
                var count = 0;
                var renterName = "";
                var newMess = "";

                //   const userList = `<li class="user-list"><a href="#adudu">${snapshot.key}</a></li>`;
                fetchChat2.on("child_added", function (snapshot2) {
                    const messages = snapshot2.val();
                    if (messages.read === false) {
                        count++;
                        console.log("red - messages.read : " + messages.read);
                    }
                    if (username != messages.username) {
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
            console.log("messages: " + messages);
            // var link = document.getElementById("link-room-detail");
            // var stringUrl = "roomDetail?hostelId=${"+messages.hostelID+"}&rid=${"+messages.roomID+"}";
            // console.log("Room URL: "+stringUrl);
            // link.href=stringUrl;
            let message2 =
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
                                                 
                                                    <a class="nav-link" href="roomDetail?hostelId=${messages.hostelID}&rid=${messages.roomID}">
                                            <i class="fe-chevrons-right"></i>
                                           Xem Phòng
                                        </a>

                                                   
                                                </div>
                                                <!-- Message: content -->

                                            </div>
                                        </div>
                                        <!-- Message: row -->

                                    </div>
                                    <!-- Message: body -->
                                </div>`;
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

            if (messages.role === '2') {
                console.log("messrole: " + messages.role)
                document.getElementById("messages").innerHTML += message2;
            } else {
                console.log("messrole else: " + messages.role)
                document.getElementById("messages").innerHTML += message3;
            }


            if (document
                .getElementById("messages") != null)
                document
                    .getElementById("end-of-chat")
                    .scrollIntoView(true);
            // document
            //     .getElementById("chatContainer")
            //     .scrollIntoView({behavior: "smooth", block: "end", inline: "nearest"});
            document
                .getElementById("chat-footer")
                .scrollIntoView(true);

        });
    }

}