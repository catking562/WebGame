package taewookim.WebGame.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import taewookim.WebGame.controller.dto.request.WebSocketResponse;
import taewookim.WebGame.controller.dto.request.WebSocketResponseType;
import taewookim.system.DataManager;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        DataManager.watingRoom.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        JsonObject obj = (JsonObject) new JsonParser().parse(message.getPayload());
        DataManager.requestPacket(session, new Gson().fromJson(obj, WebSocketResponseType.valueOf(obj.get("type").getAsString()).getClazz()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        DataManager.remove(session);
    }
}
