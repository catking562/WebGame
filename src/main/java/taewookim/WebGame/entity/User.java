package taewookim.WebGame.entity;

import jakarta.persistence.*;
import taewookim.WebGame.util.DateTimeUtil;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private LocalDateTime created;

    @OneToOne(mappedBy = "user")
    private Score score;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.created = DateTimeUtil.getNowDate();
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public void appendScore(Score score) {
        this.score = score;
        score.setUser(this);
    }

    public Score getScore() {
        return score;
    }

    public User() {

    }
}
