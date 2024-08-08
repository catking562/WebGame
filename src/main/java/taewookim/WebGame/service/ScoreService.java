package taewookim.WebGame.service;

import org.springframework.stereotype.Service;
import taewookim.WebGame.entity.Score;
import taewookim.WebGame.repository.ScoreRepository;
import taewookim.WebGame.repository.UserRepository;

import java.util.List;

@Service
public class ScoreService {

    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    public ScoreService(UserRepository userRepository, ScoreRepository scoreRepository) {
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
    }

    public Score getScoreByUser(long userid) {
        return userRepository.getUserById(userid).getScore();
    }

    public List<Score> getScoreList() {
        return scoreRepository.findTop5();
    }

}
