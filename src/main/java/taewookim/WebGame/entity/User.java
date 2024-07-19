package taewookim.WebGame.entity;

import jakarta.persistence.*;
import taewookim.WebGame.util.DateTimeUtil;

import java.time.LocalDate;
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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.created = DateTimeUtil.getNowDate();
    }

    public User() {

    }
}
