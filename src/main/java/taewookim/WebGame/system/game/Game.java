package taewookim.WebGame.system.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import taewookim.WebGame.entity.Score;
import taewookim.WebGame.repository.ScoreRepository;
import taewookim.WebGame.util.Random;
import taewookim.WebGame.util.Triangle;
import taewookim.WebGame.system.UserSocket;
import taewookim.WebGame.system.game.gameobject.Player;
import taewookim.WebGame.system.game.gameobject.Projectile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private final ScoreRepository scoreRepository;

    private final Player player;
    private final Player player1;
    private final Map<Integer, Projectile> numProjectile = new HashMap<>();
    private final Map<Projectile, Integer> projectileNum = new HashMap<>();

    private boolean isEnd = false;
    private double time = 0;
    private int status = 0;

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer1() {
        return player1;
    }

    public synchronized void requestPlayerLocation(WebSocketSession session, double x, double y) {
        if(player.getConnection().equals(session)) {
            checkIsTeleport(x, y, player);
        }else if(player1.getConnection().equals(session)) {
            checkIsTeleport(x, y, player1);
        }
    }

    private synchronized void checkIsTeleport(double x, double y, Player player) {
        /*if(Math.abs(x - player.getX())>15||Math.abs(y - player.getY())>15) {
            player.sendPlayerLocation(0, player.getX(), player.getY());
            return;
        }*/
        player.setX(x);
        player.setY(y);
    }

    public synchronized void remove(WebSocketSession session) {
        if(player.getConnection().equals(session)) {
            setEnd(1);
        }else if(player1.getConnection().equals(session)) {
            setEnd(0);
        }

    }

    public synchronized void addProjectile(Projectile projectile) {
        int i = 0;
        while(numProjectile.containsKey(i)) {
            i++;
        }
        numProjectile.put(i, projectile);
        projectileNum.put(projectile, i);
    }

    public synchronized void removeProjectile(Projectile projectile) {
        int num = projectileNum.get(projectile);
        numProjectile.remove(num);
        projectileNum.remove(projectile);
    }

    public synchronized void createProjectile() {
        boolean isX = Random.random.nextBoolean();
        boolean isPlus = Random.random.nextBoolean();
        double dig = (isX?isPlus?180:0:isPlus?90:270) +(Random.random.nextDouble()-0.5)*60;
        Projectile projectile = new Projectile(
                isX?isPlus?1180:-100:Random.random.nextDouble()*1080,
                isX?Random.random.nextDouble()*720:isPlus?820:-100,
                Triangle.getCos(dig)*150,
                Triangle.getSin(dig)*150,
                10
        );
        addProjectile(projectile);
        int num = projectileNum.get(projectile);
        player.sendProjectile(projectile, num);
        player1.sendProjectile(projectile, num);
    }

    public Game(UserSocket session, UserSocket session1, ScoreRepository scoreRepository) {
        this.player = new Player(session);
        this.player1 = new Player(session1);
        this.scoreRepository = scoreRepository;
    }

    public synchronized boolean isEnd() {
        return isEnd;
    }

    public synchronized void setEnd(int winner) {
        isEnd = true;
        player.sendEnd(winner==0?0:winner==1?1:-1);
        player1.sendEnd(winner==0?1:winner==1?0:-1);
        Score score0 = player.getUser().getScore();
        Score score1 = player1.getUser().getScore();
        switch (winner) {
            case 0:
                score0.addScore(10);
                score1.addScore(-5);
                break;
            case 1:
                score0.addScore(-5);
                score1.addScore(10);
                break;
        }
        scoreRepository.save(score0);
        scoreRepository.save(score1);
    }

    public synchronized void collisionPlayer(Projectile projectile, Player player) {
        double dx = projectile.getX() - player.getX();
        double dy = projectile.getY() - player.getY();
        double r = (projectile.getR()*0.5) + 25;
        if(dx*dx+dy*dy<r*r) {
            int num = projectileNum.get(projectile);
            this.player.sendRemoveProjectile(num);
            this.player1.sendRemoveProjectile(num);
            projectile.setEnd();
            player.damage(1);
            if(player.isDead()) {
                setEnd(player.equals(this.player)?1:player.equals(this.player1)?0:-1);
            }else {
                player.sendHp(0, player.getHp());
                if(player.equals(this.player)) {
                    player1.sendHp(1, player.getHp());
                }else if(player.equals(this.player1)) {
                    this.player.sendHp(1, player.getHp());
                }
            }
        }
    }

    public synchronized void update(double deltaTime) {
        if(isEnd) {
            return;
        }
        time += deltaTime;
        //플레이어 업데이트
        switch(status) {
            case 0:
                JsonObject ob = new JsonObject();
                ob.addProperty("type", "TurnGame");
                try{
                    player.getConnection().sendMessage(new TextMessage(new Gson().toJson(ob)));
                    player1.getConnection().sendMessage(new TextMessage(new Gson().toJson(ob)));
                }catch(Exception e) {
                }
                player.sendPlayerName(1, player1.getUserName());
                player1.sendPlayerName(1, player.getUserName());
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
        //투사체 업데이트
        ArrayList<Projectile> removing = new ArrayList<>();
        for (Projectile projectile : numProjectile.values()) {
            projectile.update(deltaTime);
            collisionPlayer(projectile, player);
            collisionPlayer(projectile, player1);
            if(projectile.isEnd()) {
                removing.add(projectile);
            }
        }
        for(Projectile projectile : removing) {
            removeProjectile(projectile);
        }
        //투사체 생성
        createProjectile();
        //
        if(time>60) {
            setEnd(-1);
            status = -1;
        }
    }

}
