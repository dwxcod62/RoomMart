package com.codebrew.roommart.utils;


import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dto.Account;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@ServerEndpoint(value = "/show-box-chat", configurator = GetHttpSessionConfigurator.class)
public class ShowBoxChatWebSocket {
    static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void handleOpen(Session session) {
        users.add(session);
    }

    @OnMessage
    public void handleMessage(String Message) throws IOException, EncodeException {
        JsonObject json = Json.createReader(new StringReader(Message)).readObject();
        System.out.println(json);
        HashMap<String, Object> map = new Gson().fromJson(json.toString(), HashMap.class);

        String sender = map.get("sender").toString();
        String receiver = map.get("receiver").toString();
        String hostelReceiverId = map.get("hostel_receiver_id").toString();
        String accountReceiverId = map.get("account_receiver_id").toString();
        String message = map.get("message").toString();
        String chat = map.get("chat").toString();
        String roomID = map.get("roomID").toString();
        String hostelID = map.get("hostelID").toString();
        String ownerId = map.get("ownerId").toString();
        String renterId = map.get("renterId").toString();
        String role = map.get("role").toString();
        String username = map.get("username").toString();
        JSONObject obj = new JSONObject();
        obj.put("message", message);
        obj.put("chat", chat);
        obj.put("roomID",roomID);
        obj.put("hostelID",hostelID);
        obj.put("ownerId",ownerId);
        obj.put("renterId",renterId);
        obj.put("role",role);
        obj.put("username",username);
        String objStr = obj.toString();
        System.out.println("message empty in show box chat socket:" + message);

        switch (sender) {
            //người gửi là owner
            case "hostel_owner":
                switch (receiver) {
                    //người nhận là hostel
                    case "hostel":
                        ArrayList<Integer> renterList = new HostelDao().getListRenterIdByHostelId(Integer.parseInt(hostelReceiverId));
                        for (Session session : users) {
                            Account account = (Account) session.getUserProperties().get("user");
                            for (int idList : renterList) {

                                if (account.getAccId() == idList){
//                                    if (message!=null){
//                                        session.getBasicRemote().sendText(message);
//                                    }else {
//                                        session.getBasicRemote().sendText(chat);
//                                    }
                                    session.getBasicRemote().sendText(objStr);

                                }
                            }
                        }
                        break;


                    case "hostel_renter":
                        for (Session session : users) {
                            Account account = (Account) session.getUserProperties().get("user");
                            if (account.getAccId() == Integer.parseInt(accountReceiverId)){
//                                if (message!=null){
//                                    session.getBasicRemote().sendText(message);
//                                }else {
//                                    session.getBasicRemote().sendText(chat);
//                                }
                                session.getBasicRemote().sendText(objStr);

                            }
                        }
                        break;
                }
                break;

            //người gửi là renter
            case "hostel_renter":
                switch (receiver) {
                    case "hostel_owner":
                        for (Session session : users) {
                            Account account = (Account) session.getUserProperties().get("user");
                            if (account.getAccId() == Integer.parseInt(accountReceiverId)) {
//                                if (message!=null){
//                                    session.getBasicRemote().sendText(message);
//                                }else {
//                                    session.getBasicRemote().sendText(chat);
//                                }
                                session.getBasicRemote().sendText(objStr);

                            };
                        }
                        break;
                }
                break;

            //người gửi là hệ thống
            case "system":
                switch (receiver) {
                    case "hostel_renter":
                        break;
                    case "hostel_owner":
                        break;
                }
                break;

            default:
                break;
        }

    }

    @OnClose
    public void handleClose(Session session) {
        users.remove(session);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

}