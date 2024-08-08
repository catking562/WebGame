package taewookim.WebGame.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int score;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Score() {

    }

    public void addScore(int score) {
        this.score += score;
    }

    public Score(User user) {
        this.user = user;
        id = user.getId();
        score = 0;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    public Long getId() {
        return id;
    }
}
