package com.fsh.demo.websocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessageObject {

    Map<String,String> keyvalues = new HashMap<String,String>();

    public void addKeyValue(String key, String value) {
        keyvalues.put(key, value);
    }

    public String getKeyValue(String key) {
        String v = keyvalues.get(key);
        return  v != null ? v : "";
    }

    /* Ugly but.... for now... */
    public static MessageObject fromJson(JsonObject jo) {
        MessageObject mo = new MessageObject();
        Set<Map.Entry<String, JsonElement>> entrySet = jo.entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet){
            mo.addKeyValue(entry.getKey(), jo.get(entry.getKey()).getAsString());
        }
        return mo;
    }
}
