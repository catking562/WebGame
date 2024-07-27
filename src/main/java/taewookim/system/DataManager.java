package taewookim.system;

import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.controller.dto.request.WebSocketRequest;
import taewookim.WebGame.controller.dto.request.WebSocketRequestType;
import taewookim.system.game.GameManager;
import taewookim.system.hansshake.HandShakeManager;
import taewookim.system.watingroom.WatingRoom;

public class DataManager {

    public static WatingRoom watingRoom;
    public static HandShakeManager watingGameManager;
    public static GameManager gameManager;

    public static void requestPacket(WebSocketSession session, Object message) {
        if(!(message instanceof WebSocketRequest)) {
            return;
        }
        WebSocketRequest response = (WebSocketRequest) message;
        try{
            WebSocketRequestType.valueOf(response.type).getWebSocketRunner()
                    .run(response, session, watingRoom, watingGameManager, gameManager);
        }catch(Exception e) {
            if(!session.isOpen()) {
                remove(session);
            }
        }
    }

    public static void remove(WebSocketSession session) {
        watingRoom.remove(session);
        watingGameManager.remove(session);
        gameManager.remove(session);
    }

}
