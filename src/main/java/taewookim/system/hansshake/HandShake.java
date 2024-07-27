package taewookim.system.hansshake;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import taewookim.system.DataManager;

import java.util.Map;

public class HandShake {

    private final WebSocketSession session;
    private final WebSocketSession session1;
    private boolean isEnd = false;
    private int status = 0;

    private boolean is = false;
    private boolean is1 = false;

    private double time = 0;

    public synchronized boolean isEnd() {
        return isEnd;
    }

    public void accept(WebSocketSession sess) {
        if(session==sess&&session.isOpen()) {
            is = true;
        }
        if(session1==sess&&session1.isOpen()) {
            is1 = true;
        }
    }

    public synchronized void remove(WebSocketSession sess) {
        if(session==sess) {
            DataManager.watingRoom.add(session);
            isEnd = true;
        }else if(session1==sess) {
            DataManager.watingRoom.add(session1);
            isEnd = true;
        }
    }

    public synchronized WebSocketSession getSession() {
        return session;
    }

    public synchronized WebSocketSession getSession1() {
        return session1;
    }

    public HandShake(
            WebSocketSession session,
            WebSocketSession session1
    ) {
        this.session = session;
        this.session1 = session1;
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
                    DataManager.watingRoom.add(session);
                    DataManager.watingRoom.add(session1);
                    isEnd = true;
                }else if(is&&is1) {
                    JsonObject ob = new JsonObject();
                    ob.addProperty("type", "TurnGame");
                    try{
                        session.sendMessage(new TextMessage(new Gson().toJson(ob)));
                        session1.sendMessage(new TextMessage(new Gson().toJson(ob)));
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    isEnd = true;
                }
                break;
        }
    }

}
