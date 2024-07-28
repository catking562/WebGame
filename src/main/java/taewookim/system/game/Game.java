package taewookim.system.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.socket.TextMessage;
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

    public void requestPlayerName(WebSocketSession session, String name) {
        if(player.getConnection().equals(session)) {
            player1.sendPlayerName(1, name);
        }else if(player1.getConnection().equals(session)) {
            player.sendPlayerName(1, name);
        }
    }

    public void requestPlayerLocation(WebSocketSession session, double x, double y) {
        if(player.getConnection().equals(session)) {
            checkIsTeleport(x, y, player);
        }else if(player1.getConnection().equals(session)) {
            checkIsTeleport(x, y, player1);
        }
    }

    private void checkIsTeleport(double x, double y, Player player) {
        /*if(Math.abs(x - player.getX())>15||Math.abs(y - player.getY())>15) {
            player.sendPlayerLocation(1, player.getX(), player.getY());
            return;
        }*/
        player.setX(x);
        player.setY(y);
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
                JsonObject ob = new JsonObject();
                ob.addProperty("type", "TurnGame");
                try{
                    player.getConnection().sendMessage(new TextMessage(new Gson().toJson(ob)));
                    player1.getConnection().sendMessage(new TextMessage(new Gson().toJson(ob)));
                }catch(Exception e) {
                    e.printStackTrace();
                }
                player.setX(100);
                player1.setX(980);
                player.sendPlayerLocation(0, player.getX(), player.getY());
                player1.sendPlayerLocation(0, player1.getX(), player1.getY());
                player.sendPlayerLocation(1, player1.getX(), player1.getY());
                player1.sendPlayerLocation(1, player.getX(), player.getY());
                status++;
                break;
            case 1:
                player.sendPlayerLocation(1, player1.getX(), player1.getY());
                player1.sendPlayerLocation(1, player.getX(), player.getY());
                break;
        }
    }

}
