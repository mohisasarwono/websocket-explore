package com.websocket.controller;

import com.websocket.test.ServerWebSocketHandler;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@RestController
@RequestMapping("test")
@AllArgsConstructor
public class TestController {

    private final ServerWebSocketHandler serverWebSocketHandler;

    @PostMapping("send")
    public void sendMessage() throws IOException {
        for (WebSocketSession webSocketSession: serverWebSocketHandler.getSessions()){
            if(serverWebSocketHandler.sessionId.equals(webSocketSession.getId()))
                webSocketSession.sendMessage(new TextMessage("{\"field\": \"test\"}"));
        }
    }

    @PostMapping("get")
    public void getSession(){
        System.out.println(serverWebSocketHandler.getSessions());
    }

    @MessageMapping("message")
    public void processMessageFromClient(@Payload String message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println(sessionId);
    }

}
