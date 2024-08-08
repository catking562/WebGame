package taewookim.WebGame.controller.dto.request.websocket;

import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.system.check.CheckSystem;
import taewookim.WebGame.system.game.GameManager;
import taewookim.WebGame.system.hansshake.HandShakeManager;
import taewookim.WebGame.system.watingroom.WatingRoom;

public enum WebSocketRequestType {

    Normal(WebSocketRequest.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, CheckSystem checkSystem, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
        }
    }),
    AcceptMatching(WebSocketRequest.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, CheckSystem checkSystem, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
            handshake.findGamebySocket(session).accept(session);
        }
    }),
    PlayerName(WebSocketRequestPlayerName.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, CheckSystem checkSystem, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
            WebSocketRequestPlayerName data = (WebSocketRequestPlayerName) packet;
            checkSystem.putUserName(session, data.name);
        }
    }),
    PlayerLocation(WebSocketRequestPlayerLocation.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, CheckSystem checkSystem, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
            WebSocketRequestPlayerLocation data = (WebSocketRequestPlayerLocation) packet;
            gameManager.findGamebySession(session).requestPlayerLocation(session, data.x, data.y);
        }
    }),
    ;

    private Class<? extends WebSocketRequest> clazz;
    private WebSocketRunner socketrunner;

    WebSocketRequestType(
            Class<? extends WebSocketRequest> clazz, WebSocketRunner socketrunner
    ) {
        this.clazz = clazz;
        this.socketrunner = socketrunner;
    }

    public WebSocketRunner getWebSocketRunner() {
        return socketrunner;
    }

    public Class<? extends WebSocketRequest> getClazz() {
        return clazz;
    }

}
