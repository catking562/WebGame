package taewookim.WebGame.proxyservice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.exception.ExceptionType;
import taewookim.WebGame.exception.HTTPApiException;
import taewookim.WebGame.service.LoginService;
import taewookim.WebGame.service.UserService;

@Service
public class LoginProxyService {

    private final LoginService loginservice;
    private final UserService userservice;

    public LoginProxyService(LoginService loginservice, UserService userservice) {
        this.loginservice = loginservice;
        this.userservice = userservice;
    }

    public ResponseEntity<Boolean> login(
            String username, String password, HttpServletRequest request
    ) throws HTTPApiException {
        if(username==null||password==null) {
            return ResponseEntity.ok(false);
        }
        return loginservice.login(username, password, request);
    }

    public ResponseEntity<Boolean> regist(
            String username, String password
    ) throws HTTPApiException {
        if(username==null||password==null) {
            return ResponseEntity.ok(false);
        }
        if(userservice.isExistName(username)) {
            throw new HTTPApiException(ExceptionType.User_CantCollisionUserName);
        }
        return loginservice.regist(username, password);
    }

}
