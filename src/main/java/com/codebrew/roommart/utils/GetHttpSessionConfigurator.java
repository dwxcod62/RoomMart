package com.codebrew.roommart.utils;



import com.codebrew.roommart.dto.Account;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator
{
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response){
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        Account user = (Account) httpSession.getAttribute("USER");
        config.getUserProperties().put("user", user);
    }

}
