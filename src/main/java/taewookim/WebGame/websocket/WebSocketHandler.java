package taewookim.WebGame.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import taewookim.WebGame.controller.dto.request.websocket.WebSocketRequestType;
import taewookim.WebGame.system.MainSystem;
import taewookim.WebGame.system.UserSocket;
import taewookim.WebGame.system.game.AccessSystem;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final AccessSystem System;

    public WebSocketHandler(AccessSystem System) {
        this.System = System;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.getCheckSystem().addSocket(new UserSocket(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        JsonObject obj = (JsonObject) new JsonParser().parse(message.getPayload());
        System.requestPacket(session, new Gson().fromJson(obj, WebSocketRequestType.valueOf(obj.get("type").getAsString()).getClazz()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.remove(session);
    }
}
