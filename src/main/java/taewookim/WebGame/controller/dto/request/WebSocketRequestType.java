package taewookim.WebGame.controller.dto.request;

import org.springframework.web.socket.WebSocketSession;
import taewookim.system.game.GameManager;
import taewookim.system.hansshake.HandShakeManager;
import taewookim.system.watingroom.WatingRoom;

public enum WebSocketRequestType {

    Normal(WebSocketRequest.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
        }
    }),
    AcceptMatching(WebSocketRequest.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
            handshake.findGamebySocket(session).accept(session);
        }
    }),
    PlayerName(WebSocketRequestPlayerName.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
            WebSocketRequestPlayerName data = (WebSocketRequestPlayerName) packet;
            gameManager.findGamebySession(session).requestPlayerName(session, data.name);
        }
    }),
    PlayerLocation(WebSocketRequestPlayerLocation.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketRequest packet, WebSocketSession session, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
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
