package taewookim.WebGame.service;

import org.springframework.stereotype.Service;
import taewookim.WebGame.entity.Score;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isExistName(String username) {
        return userRepository.getUserByName(username) != null;
    }

    public Score getUserScore(String username) {
        return userRepository.getUserByName(username).getScore();
    }

    public boolean isExistId(long id) {
        return userRepository.isExistUser(id);
    }

}
