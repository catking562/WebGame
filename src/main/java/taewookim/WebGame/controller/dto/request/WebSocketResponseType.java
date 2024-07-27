package taewookim.WebGame.controller.dto.request;

import org.springframework.web.socket.WebSocketSession;
import taewookim.system.game.GameManager;
import taewookim.system.hansshake.HandShakeManager;
import taewookim.system.watingroom.WatingRoom;

public enum WebSocketResponseType {

    Normal(WebSocketResponse.class, new WebSocketRunner() {
        @Override
        public void run(WebSocketResponse packet, WebSocketSession session, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
        }
    }),
    AcceptMatching(WebSocketResponse.class, new WebSocketRunner() {

        @Override
        public void run(WebSocketResponse packet, WebSocketSession session, WatingRoom watingRoom, HandShakeManager handshake, GameManager gameManager) {
            handshake.findGamebySocket(session).accept(session);
        }
    }),
    ;

    private Class<? extends WebSocketResponse> clazz;
    private WebSocketRunner socketrunner;

    WebSocketResponseType(
            Class<? extends WebSocketResponse> clazz, WebSocketRunner socketrunner
    ) {
        this.clazz = clazz;
        this.socketrunner = socketrunner;
    }

    public WebSocketRunner getWebSocketRunner() {
        return socketrunner;
    }

    public Class<? extends WebSocketResponse> getClazz() {
        return clazz;
    }

}
