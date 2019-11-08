package com.fsh.demo.websocket;

import lombok.Data;
import lombok.NonNull;

@Data
public class MessageHeader {
    public @NonNull String from;
    public @NonNull String to;
    public @NonNull String klass;
}
