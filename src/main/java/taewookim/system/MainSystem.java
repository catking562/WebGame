package taewookim.system;

import taewookim.system.game.GameManager;
import taewookim.system.hansshake.HandShakeManager;
import taewookim.system.watingroom.WatingRoom;

public class MainSystem extends Thread {

    private final WatingRoom watingroom;
    private final HandShakeManager watingGameManager;
    private final GameManager gameManager;
    private boolean isEnd = false;

    private final int maxDelay = 1000/10;
    private double deltaTime = 0;

    private Long lastUpdate = System.currentTimeMillis();

    public MainSystem() {
        this.watingroom = new WatingRoom();
        this.watingGameManager = new HandShakeManager();
        this.gameManager = new GameManager();
        DataManager.watingRoom = this.watingroom;
        DataManager.watingGameManager = this.watingGameManager;
        DataManager.gameManager = this.gameManager;
    }

    public void setEnd() {
        isEnd = true;
    }

    public void run() {
        while(!isEnd) {
            long before = System.currentTimeMillis();
            deltaTime = (System.currentTimeMillis() - lastUpdate)/1000d;
            lastUpdate = System.currentTimeMillis();
            //코드 시작
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
