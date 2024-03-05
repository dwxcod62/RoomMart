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

@ServerEndpoint(value = "/push-noti-websocket", configurator = GetHttpSessionConfigurator.class)
public class PushNotiWebSocket {
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
        System.out.println("hostel_receiver_id: "+hostelReceiverId);
        String accountReceiverId = map.get("account_receiver_id").toString();
        String message = map.get("message").toString();
        String chat = map.get("chat").toString();
        String rid = map.get("rid").toString();
        String hid = map.get("hid").toString();
        JSONObject obj = new JSONObject();
        obj.put("message", message);
        obj.put("chat", chat);
        obj.put("rid",rid);
        obj.put("hid",hid);
        String objStr = obj.toString();
        System.out.println("message empty in push notify:" + message);

        switch (sender) {
            //người gửi là owner
            case "hostel_owner":
                switch (receiver) {
                    //người nhận là hostel
                    case "hostel":
                        ArrayList<Integer> renterList = new HostelDao().getListRenterIdByHostelId(Integer.parseInt(hostelReceiverId));
                        System.out.println(renterList);
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