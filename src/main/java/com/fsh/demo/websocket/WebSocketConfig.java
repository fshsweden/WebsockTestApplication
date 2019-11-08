package com.fsh.demo.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("WebSocketConfig.registerWebSocketHandlers");
        registry.addHandler(myHandler(), "/sockjs").setAllowedOrigins("*").withSockJS();;
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new WebSocketHandler();
    }
}
