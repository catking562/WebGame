package taewookim.WebGame.system.hansshake;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HandShakeManager {

    private final List<HandShake> watinggames = new ArrayList<>();
    private final Map<WebSocketSession, HandShake> sessions = new HashMap<>();

    public HandShakeManager() {

    }

    public synchronized void remove(WebSocketSession session) {
        HandShake hs = findGamebySocket(session);
        if (hs != null) {
            hs.remove(session);
        }
    }

    public synchronized HandShake findGamebySocket(WebSocketSession session) {
        return sessions.get(session);
    }

    public synchronized void add(HandShake game) {
        watinggames.add(game);
        sessions.put(game.getSession().getSession(), game);
        sessions.put(game.getSession1().getSession(), game);
    }

    public synchronized void update(Double deltaTime) {
        List<HandShake> removing = new ArrayList<>();
        for (HandShake game : watinggames) {
            game.update(deltaTime);
            if(game.isEnd()) {
                sessions.remove(game.getSession().getSession());
                sessions.remove(game.getSession1().getSession());
                removing.add(game);
            }
        }
        watinggames.removeAll(removing);
    }

}
