package taewookim.WebGame.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taewookim.WebGame.entity.Score;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.exception.HTTPApiException;
import taewookim.WebGame.repository.ScoreRepository;
import taewookim.WebGame.repository.UserRepository;
import taewookim.WebGame.util.SHA;

@Service
@Transactional
public class LoginService {

    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    public LoginService(UserRepository userRepository, ScoreRepository scoreRepository) {
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
    }

    public Boolean login(String username, String password, HttpServletRequest request
    ) throws HTTPApiException {
        User user = userRepository.getUserWithLogin(username, SHA.sha3_512(password));
        if (user == null) {
            return false;
        }
        if(user.getScore()==null) {
            Score score = new Score();
            user.appendScore(score);
            scoreRepository.save(score);
        }
        request.getSession().setAttribute("loginUser", user);
        return true;
    }

    @Transactional
    public Boolean regist(
            String username, String password
    ) throws HTTPApiException {
        User user = new User(username, SHA.sha3_512(password));
        userRepository.save(user);
        return true;
    }

}
