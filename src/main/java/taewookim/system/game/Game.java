package taewookim.system.game;

import com.google.gson.Gson;
import org.springframework.web.socket.WebSocketSession;

public class Game {

    private final Player player;
    private final Player player1;

    private boolean isEnd = false;
    private double time = 0;
    private int status = 0;

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void remove(WebSocketSession session) {
        if(player.getConnection().equals(session)) {
            isEnd = true;
        }else if(player1.getConnection().equals(session)) {
            isEnd = true;
        }
    }

    public Game(WebSocketSession session, WebSocketSession session1) {
        this.player = new Player(session);
        this.player1 = new Player(session1);
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void update(double deltaTime) {
        if(isEnd) {
            return;
        }
        time += deltaTime;
        switch(status) {
            case 0:
                player.setX(100);
                player1.setX(980);
                player.sendPlayerLocation(0, player.getX(), player.getY());
                player1.sendPlayerLocation(0, player1.getX(), player1.getY());
                player.sendPlayerLocation(1, player1.getX(), player1.getY());
                player1.sendPlayerLocation(1, player.getX(), player.getY());
                status++;
                break;
            case 1:

                break;
        }
    }

}
