package taewookim.system;

import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.controller.dto.request.WebSocketResponse;
import taewookim.WebGame.controller.dto.request.WebSocketResponseType;
import taewookim.system.game.GameManager;
import taewookim.system.hansshake.HandShakeManager;
import taewookim.system.watingroom.WatingRoom;

public class DataManager {

    public static WatingRoom watingRoom;
    public static HandShakeManager watingGameManager;
    public static GameManager gameManager;

    public static void requestPacket(WebSocketSession session, Object message) {
        if(!(message instanceof WebSocketResponse)) {
            return;
        }
        WebSocketResponse response = (WebSocketResponse) message;
        WebSocketResponseType.valueOf(response.type).getWebSocketRunner()
                .run(response, session, watingRoom, watingGameManager, gameManager);
    }

    public static void remove(WebSocketSession session) {
        watingRoom.remove(session);
        watingGameManager.remove(session);
        gameManager.remove(session);
    }

}
