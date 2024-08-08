package taewookim.WebGame.system.watingroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.repository.ScoreRepository;
import taewookim.WebGame.system.MainSystem;
import taewookim.WebGame.system.UserSocket;
import taewookim.WebGame.system.game.AccessSystem;
import taewookim.WebGame.system.game.GameManager;
import taewookim.WebGame.system.hansshake.HandShake;
import taewookim.WebGame.system.hansshake.HandShakeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WatingRoom {

    private final HandShakeManager handShakeManager;
    private final GameManager gameManager;
    private final ScoreRepository scoreRepository;

    private final List<UserSocket> wating = new ArrayList<>();
    private final Map<WebSocketSession, UserSocket> sessions = new HashMap<>();

    public WatingRoom(HandShakeManager handShakeManager, GameManager gameManager, ScoreRepository scoreRepository) {
        this.handShakeManager = handShakeManager;
        this.gameManager = gameManager;
        this.scoreRepository = scoreRepository;
    }

    public synchronized void add(UserSocket session) {
        wating.add(session);
        sessions.put(session.getSession(), session);
    }

    public synchronized void remove(WebSocketSession session) {
        wating.remove(sessions.get(session));
        sessions.remove(session);
    }

    public synchronized void update(double deltaTime) {
        List<UserSocket> removing = new ArrayList<>();
        UserSocket sess = null;
        for(UserSocket session : wating) {
            if(!session.isOpen()) {
                removing.add(session);
                continue;
            }
            if(sess!=null) {
                handShakeManager.add(new HandShake(sess, session, this, gameManager, scoreRepository));
                removing.add(session);
                removing.add(sess);
                sess = null;
            }else {
                sess = session;
            }
        }
        //삭제과정
        for(UserSocket session : removing) {
            sessions.remove(session.getSession());
        }
        wating.removeAll(removing);
    }

}
