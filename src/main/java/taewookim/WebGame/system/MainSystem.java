package taewookim.WebGame.system;

import jakarta.annotation.PostConstruct;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.controller.dto.request.websocket.WebSocketRequest;
import taewookim.WebGame.controller.dto.request.websocket.WebSocketRequestType;
import taewookim.WebGame.system.check.CheckSystem;
import taewookim.WebGame.system.game.GameManager;
import taewookim.WebGame.system.watingroom.WatingRoom;
import taewookim.WebGame.system.hansshake.HandShakeManager;

@Component
public class MainSystem {

    private final CheckSystem checkSystem;
    private final WatingRoom watingroom;
    private final HandShakeManager watingGameManager;
    private final GameManager gameManager;
    private final HandShakeManager handShakeManager;
    private boolean isEnd = false;

    private final int maxDelay = 1000/10;
    private double deltaTime = 0;

    private Long lastUpdate = System.currentTimeMillis();

    public MainSystem(CheckSystem checkSystem, WatingRoom watingroom, HandShakeManager watingGameManager, GameManager gameManager, HandShakeManager handShakeManager) {
        this.checkSystem = checkSystem;
        this.watingroom = watingroom;
        this.watingGameManager = watingGameManager;
        this.gameManager = gameManager;
        this.handShakeManager = handShakeManager;
    }

    public void setEnd() {
        isEnd = true;
    }

    @Async
    public void run() {
        while(!isEnd) {
            long before = System.currentTimeMillis();
            deltaTime = (System.currentTimeMillis() - lastUpdate)/1000d;
            lastUpdate = System.currentTimeMillis();
            //코드 시작
            checkSystem.update(deltaTime);
            watingroom.update(deltaTime);
            watingGameManager.update(deltaTime);
            gameManager.update(deltaTime);
            //코드 끝
            long delta = before - System.currentTimeMillis();
            long delay = maxDelay-delta;
            if(delay>0) {
                try{
                    Thread.sleep(delay);
                }catch (Exception e) {
                }
            }
        }
    }
}
