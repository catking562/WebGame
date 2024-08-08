package taewookim.WebGame.proxyservice;

import org.springframework.stereotype.Service;
import taewookim.WebGame.controller.dto.request.http.UserRequest;
import taewookim.WebGame.controller.dto.response.http.ScoreResponse;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.service.UserService;
import taewookim.WebGame.system.MainSystem;
import taewookim.WebGame.system.game.AccessSystem;

import java.util.Objects;

@Service
public class UserProxyService {

    private final UserService userService;
    private final AccessSystem system;

    public UserProxyService(UserService userService, AccessSystem system) {
        this.userService = userService;
        this.system = system;
    }

    public ScoreResponse getUserScore(String username) {
        return ScoreResponse.fromScore(userService.getUserScore(username));
    }

    public boolean isSame(User user, UserRequest request) {
        if(user==null) {
            return false;
        }
        boolean is = user.getUserName().equalsIgnoreCase(request.username())
                &&Objects.equals(user.getId(), request.id());
        if(is) {
            system.getCheckSystem().putUserData(user);
        }
        return is;
    }

}
