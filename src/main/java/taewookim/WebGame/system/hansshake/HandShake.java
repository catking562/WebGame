package taewookim.WebGame.system.hansshake;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.repository.ScoreRepository;
import taewookim.WebGame.system.MainSystem;
import taewookim.WebGame.system.UserSocket;
import taewookim.WebGame.system.game.AccessSystem;
import taewookim.WebGame.system.game.Game;
import taewookim.WebGame.system.game.GameManager;
import taewookim.WebGame.system.watingroom.WatingRoom;

public class HandShake {

    private final WatingRoom watingRoom;
    private final GameManager gameManager;
    private final ScoreRepository scoreRepository;

    private final UserSocket session;
    private final UserSocket session1;
    private boolean isEnd = false;
    private int status = 0;

    private boolean is = false;
    private boolean is1 = false;

    private double time = 0;

    public synchronized boolean isEnd() {
        return isEnd;
    }

    public void accept(WebSocketSession sess) {
        if(session.getSession()==sess&&session.isOpen()) {
            is = true;
        }
        if(session1.getSession()==sess&&session1.isOpen()) {
            is1 = true;
        }
    }

    public synchronized void remove(WebSocketSession sess) {
        if(session.getSession()==sess) {
            watingRoom.add(session);
            isEnd = true;
        }else if(session1.getSession()==sess) {
            watingRoom.add(session1);
            isEnd = true;
        }
    }

    public synchronized UserSocket getSession() {
        return session;
    }

    public synchronized UserSocket getSession1() {
        return session1;
    }

    public HandShake(
            UserSocket session,
            UserSocket session1,
            WatingRoom watingRoom,
            GameManager gameManager,
            ScoreRepository scoreRepository
    ) {
        this.session = session;
        this.session1 = session1;
        this.watingRoom = watingRoom;
        this.gameManager = gameManager;
        this.scoreRepository = scoreRepository;
    }

    public synchronized void update(Double deltaTime) {
        if(isEnd) {
            return;
        }
        time += deltaTime;
        switch (status) {
            case 0:
                JsonObject obj = new JsonObject();
                obj.addProperty("type", "FinishMatching");
                try{
                    session.sendMessage(new TextMessage(new Gson().toJson(obj)));
                    session1.sendMessage(new TextMessage(new Gson().toJson(obj)));
                }catch(Exception e) {
                    e.printStackTrace();
                }
                status++;
                break;
            case 1:
                if(time>5) {
                    status = -1;
                    watingRoom.add(session);
                    watingRoom.add(session1);
                    isEnd = true;
                }else if(is&&is1) {
                    gameManager.add(new Game(session, session1, scoreRepository));
                    isEnd = true;
                }
                break;
        }
    }

}
