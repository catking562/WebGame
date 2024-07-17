package taewookim.WebGame.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.exception.HTTPApiException;
import taewookim.WebGame.repository.UserRepository;

@Service
@Transactional
public class LoginService {

    UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Boolean> login(String username, String password, HttpServletRequest request
    ) throws HTTPApiException {
        User user = userRepository.getUserWithLogin(username, password);
        if (user == null) {
            return ResponseEntity.ok(false);
        }
        request.getSession().setAttribute("loginUser", user);
        return ResponseEntity.ok(true);
    }

    @Transactional
    public ResponseEntity<Boolean> regist(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
        return ResponseEntity.ok(true);
    }

}
