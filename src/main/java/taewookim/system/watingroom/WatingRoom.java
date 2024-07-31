package taewookim.system.watingroom;

import org.springframework.web.socket.WebSocketSession;
import taewookim.system.DataManager;
import taewookim.system.hansshake.HandShake;

import java.util.ArrayList;
import java.util.List;

public class WatingRoom {

    private final List<WebSocketSession> wating = new ArrayList<>();

    public WatingRoom() {
    }

    public synchronized void add(WebSocketSession session) {
        wating.add(session);
    }

    public synchronized void remove(WebSocketSession session) {
        wating.remove(session);
    }

    public synchronized void update(double deltaTime) {
        List<WebSocketSession> removing = new ArrayList<>();
        WebSocketSession sess = null;
        for(WebSocketSession session : wating) {
            if(!session.isOpen()) {
                removing.add(session);
                continue;
            }
            if(sess!=null) {
                DataManager.watingGameManager.add(new HandShake(sess, session));
                removing.add(session);
                removing.add(sess);
                sess = null;
            }else {
                sess = session;
            }
        }
        wating.removeAll(removing);
    }

}
