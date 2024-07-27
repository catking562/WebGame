package taewookim.WebGame.controller.dto.request;

import org.springframework.web.socket.WebSocketSession;
import taewookim.system.game.GameManager;
import taewookim.system.hansshake.HandShakeManager;
import taewookim.system.watingroom.WatingRoom;

public abstract class WebSocketRunner {

    public abstract void run(
            WebSocketRequest packet,
            WebSocketSession session,
            WatingRoom watingRoom,
            HandShakeManager handshake,
            GameManager gameManager
    );

}
