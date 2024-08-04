package taewookim.system.game.gameobject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

public class Player {

    private final WebSocketSession connect;

    private double x = 0;
    private double y = 360;

    private int hp;

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
        hp = 10;
    }

    public void damage(int damage) {
        hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
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

    public void sendRemoveProjectile(int num) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "RemoveProjectile");
        object.addProperty("num", num);
        try{
            connect.sendMessage(new TextMessage(new Gson().toJson(object)));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getHp() {
        return hp;
    }

    public void sendHp(int num, int hp) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "Hp");
        object.addProperty("num", num);
        object.addProperty("hp", hp);
        try{
            connect.sendMessage(new TextMessage(new Gson().toJson(object)));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendProjectile(Projectile projectile, int num) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "CreateProjectile");
        object.addProperty("x", projectile.getX());
        object.addProperty("y", projectile.getY());
        object.addProperty("dx", projectile.getDx());
        object.addProperty("dy", projectile.getDy());
        object.addProperty("r", projectile.getR());
        object.addProperty("num", num);
        try{
            connect.sendMessage(new TextMessage(new Gson().toJson(object)));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEnd(int winner) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "GameEnd");
        object.addProperty("winner", winner);
        try{
            connect.sendMessage(new TextMessage(new Gson().toJson(object)));
        }catch(Exception e) {
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
