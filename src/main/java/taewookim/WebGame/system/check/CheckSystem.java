package taewookim.WebGame.system.check;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.system.MainSystem;
import taewookim.WebGame.system.UserSocket;
import taewookim.WebGame.system.game.AccessSystem;
import taewookim.WebGame.system.watingroom.WatingRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CheckSystem {

    private final WatingRoom watingRoom;

    private final ArrayList<UserSocket> sockets = new ArrayList<>();
    private final Map<WebSocketSession, UserSocket> sessions = new HashMap<>();
    private final Map<String, UserSocket> users = new HashMap<>();

    public CheckSystem(WatingRoom watingRoom) {
        this.watingRoom = watingRoom;
    }

    public synchronized void putUserData(User user) {
        UserSocket socket = users.get(user.getUserName());
        if (socket == null) {
            return;
        }
        socket.setUser(user);
    }

    public synchronized void requestUserData(UserSocket socket) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "RequireUser");
        socket.sendMessage(new TextMessage(new Gson().toJson(object)));
    }

    public synchronized void requireUserName(UserSocket socket) {
        JsonObject object = new JsonObject();
        object.addProperty("type", "RequireName");
        socket.sendMessage(new TextMessage(new Gson().toJson(object)));
    }

    public synchronized void putUserName(WebSocketSession session, String name) {
        UserSocket socket = sessions.get(session);
        if(socket == null) {
            return;
        }
        socket.setName(name);
        users.put(name, socket);
        requestUserData(socket);
    }

    public synchronized void addSocket(UserSocket socket) {
        sockets.add(socket);
        sessions.put(socket.getSession(), socket);
        requireUserName(socket);
    }

    public synchronized void remove(WebSocketSession socket) {
        sockets.remove(sessions.get(socket));
        sessions.remove(socket);
    }

    public synchronized void update(double deltatime) {
        ArrayList<UserSocket> removing = new ArrayList<>();
        Long now = System.currentTimeMillis();
        for (UserSocket socket : sockets) {
            if(now - socket.getConnectTime()>5000) {
                removing.add(socket);
            }else if(socket.isCheck()) {
                watingRoom.add(socket);
                removing.add(socket);
            }
        }
        sockets.removeAll(removing);
    }

}
