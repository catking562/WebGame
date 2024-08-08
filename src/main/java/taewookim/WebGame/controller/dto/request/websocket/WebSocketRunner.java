package taewookim.WebGame.controller.dto.request.websocket;

import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.system.check.CheckSystem;
import taewookim.WebGame.system.game.GameManager;
import taewookim.WebGame.system.hansshake.HandShakeManager;
import taewookim.WebGame.system.watingroom.WatingRoom;

public abstract class WebSocketRunner {

    public abstract void run(
            WebSocketRequest packet,
            WebSocketSession session,
            CheckSystem checkSystem,
            WatingRoom watingRoom,
            HandShakeManager handshake,
            GameManager gameManager
    );

}
