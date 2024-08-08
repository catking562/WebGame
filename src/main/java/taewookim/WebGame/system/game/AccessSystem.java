package taewookim.WebGame.system.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.controller.dto.request.websocket.WebSocketRequest;
import taewookim.WebGame.controller.dto.request.websocket.WebSocketRequestType;
import taewookim.WebGame.system.MainSystem;
import taewookim.WebGame.system.check.CheckSystem;
import taewookim.WebGame.system.hansshake.HandShakeManager;
import taewookim.WebGame.system.watingroom.WatingRoom;

@Component
public class AccessSystem {

    private final CheckSystem checkSystem;
    private final WatingRoom watingRoom;
    private final HandShakeManager handShakeManager;
    private final GameManager gameManager;

    public CheckSystem getCheckSystem() {
        return checkSystem;
    }

    public WatingRoom getWatingRoom() {
        return watingRoom;
    }

    public HandShakeManager getHandShakeManager() {
        return handShakeManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public AccessSystem(CheckSystem checkSystem, WatingRoom watingRoom, HandShakeManager handShakeManager, GameManager gameManager) {
        this.checkSystem = checkSystem;
        this.watingRoom = watingRoom;
        this.handShakeManager = handShakeManager;
        this.gameManager = gameManager;
    }

    public void requestPacket(WebSocketSession session, Object message) {
        if(!(message instanceof WebSocketRequest)) {
            return;
        }
        WebSocketRequest response = (WebSocketRequest) message;
        try{
            WebSocketRequestType.valueOf(response.type).getWebSocketRunner()
                    .run(response, session, checkSystem, watingRoom, handShakeManager, gameManager);
        }catch(Exception e) {
            if(!session.isOpen()) {
                remove(session);
            }
        }
    }

    public void remove(WebSocketSession session) {
        checkSystem.remove(session);
        watingRoom.remove(session);
        handShakeManager.remove(session);
        gameManager.remove(session);
    }

}
