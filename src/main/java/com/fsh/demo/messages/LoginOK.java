package com.fsh.demo.messages;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginOK {
    @NonNull
    private String sessionId;
}
