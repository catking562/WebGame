package taewookim.WebGame.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.exception.HTTPApiException;
import taewookim.WebGame.proxyservice.LoginProxyService;

@Controller
public class LoginController {

    private final LoginProxyService loginproxyservice;

    public LoginController(LoginProxyService loginproxyservice) {
        this.loginproxyservice = loginproxyservice;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/regist")
    public String regist() {
        return "regist";
    }

    @GetMapping("/loginuser")
    public ResponseEntity<User> getLoginedUser(
            @SessionAttribute(name = "loginUser")User loginUser
            ) {
        return ResponseEntity.ok(loginUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            HttpServletRequest request
    ) throws HTTPApiException {
        return loginproxyservice.login(username, password, request);
    }

    @PostMapping("/regist")
    public ResponseEntity<Boolean> register(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password
    ) throws HTTPApiException {
        return loginproxyservice.regist(username, password);
    }

}
