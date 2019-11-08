package com.fsh.demo.websocket;

import com.fsh.demo.messages.LoginOK;
import com.fsh.demo.services.MyMessageBroker;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    private Gson gson = new Gson();

    MyMessageBroker myMessageBroker = new MyMessageBroker();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocketHandler.afterConnectionClosed");
        myMessageBroker.unregisterSession(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocketHandler.afterConnectionEstablished with Session ID " + session.getId());
        myMessageBroker.registerSession(session);

        String json = gson.toJson(new LoginOK(session.getId()));

        System.out.println("sending back " + json);
        myMessageBroker.sendToSession(session.getId(), json);
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message from session " + session.getId() + " Received:" + message.getPayload());

        // What if the message is NOT Json???
        JsonObject  jsonobj = gson.fromJson(message.getPayload(), JsonObject.class);

        // session.sendMessage(message);
        myMessageBroker.handleIncomingMessage(session, jsonobj);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        session.sendMessage(message);
    }
}
