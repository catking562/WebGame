package taewookim.system.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class Player {

    private final WebSocketSession connect;

    private double x = 0;
    private double y = 360;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public WebSocketSession getConnection() {
        return connect;
    }

    public Player(WebSocketSession session) {
        connect = session;
    }

    public void sendPlayerLocation(int num, double x, double y) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "PlayerLocation");
        object.addProperty("num", num);
        object.addProperty("x", x);
        object.addProperty("y", y);
        try{
            connect.sendMessage(new TextMessage(new Gson().toJson(object)));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPlayerName(int num, String name) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "PlayerName");
        object.addProperty("num", num);
        object.addProperty("name", name);
        try{
            connect.sendMessage(new TextMessage(new Gson().toJson(object)));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
