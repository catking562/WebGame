package taewookim.WebGame.system;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.entity.User;

public class UserSocket {

    private final WebSocketSession socket;
    private User user = null;
    private final Long connectTime;
    private String name = null;

    public UserSocket(WebSocketSession socket) {
        this.socket = socket;
        connectTime = System.currentTimeMillis();
    }

    public Long getConnectTime() {
        return connectTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return socket.isOpen();
    }

    public void sendMessage(TextMessage message) {
        try{
            socket.sendMessage(message);
        }catch(Exception e) {
        }
    }

    public WebSocketSession getSession() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (this.user == null) {
            this.user = user;
        }
    }

    public boolean isCheck() {
        return user != null&&socket.isOpen();
    }

}
