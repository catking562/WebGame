package taewookim.system.game;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private final List<Game> games = new ArrayList<Game>();
    private final Map<WebSocketSession, Game> sessions = new HashMap<>();

    public GameManager() {

    }

    public synchronized Game findGamebySession(WebSocketSession session) {
        return sessions.get(session);
    }

    public synchronized void remove(WebSocketSession session) {
        Game game = findGamebySession(session);
        if (game != null) {
            game.remove(session);
        }
    }

    public synchronized void add(Game game) {
        games.add(game);
        sessions.put(game.getPlayer().getConnection(), game);
        sessions.put(game.getPlayer1().getConnection(), game);
    }

    public synchronized void update(double deltaTime) {
        List<Game> removed = new ArrayList<>();
        for (Game game : games) {
            game.update(deltaTime);
            if(game.isEnd()) {
                sessions.remove(game.getPlayer().getConnection());
                sessions.remove(game.getPlayer1().getConnection());
                removed.add(game);
            }
        }
        games.removeAll(removed);
    }

}
