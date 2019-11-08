package com.fsh.demo.services;

import com.fsh.demo.websocket.MessageHeader;
import com.fsh.demo.websocket.MessageObject;
import com.fsh.demo.websocket.MessageType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyMessageBroker {

    private Gson gson = new Gson();
    Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();
    Map<String, List<String>> subscriptions = new HashMap<String, List<String>>();

    public boolean handleIncomingMessage(WebSocketSession session, JsonObject msg) {

        MessageObject mo = MessageObject.fromJson(msg);
        String from =mo.getKeyValue("header-from");
        String to=mo.getKeyValue("header-to");
        String klass=mo.getKeyValue("header-class");

        MessageHeader mh = new MessageHeader(from,to,klass);

        /*
            Directed message or broadcast
         */
        if (klass.equals("*")) {
            broadcast(mo);
        }
        else {
            // session.getId() is stored in "to" field
            // this should change to "username" or something!
            sendToSession(session.getId(), mo);
        }

        /*
             Subscriptions to this "klass"
             Do we want more specific subscriptions? Like "klass" and message details?
         */
//        subscriptions.get(klass).forEach((s) ->  {
//
//        });


//        MessageType mt = MessageType.valueOf(msg.get("message").getAsString());
//        switch (mt) {
//            case PING:
//                break;
//            case MARKET_DEPTH:
//                break;
//            case MARKET_PRICE:
//                break;
//            default:
//                System.out.println("Unknown message!");
//        }
        return true;
    }

    public boolean registerSession(WebSocketSession session) {
        sessions.put(session.getId(), session);
        return true;
    }
    public boolean unregisterSession(WebSocketSession session) {
        // bug: lookup session
        sessions.remove(session.getId());
        return true;
    }

    public void broadcast(Object msg) {
        String json = gson.toJson(msg);
        TextMessage tm = new TextMessage(json);
        sessions.forEach((k,v) -> {
            try {
                v.sendMessage(tm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendToSession(String id, Object msg) {
        WebSocketSession session = sessions.get(id);
        if (session != null) {
            try {
                String json = gson.toJson(msg);
                session.sendMessage(new TextMessage(json));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Can't send to session! Session <" + id + "> not found!");
        }
    }

    public void sendToSession(String id, String msg) {
        WebSocketSession session = sessions.get(id);
        try {
            session.sendMessage(new TextMessage(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
