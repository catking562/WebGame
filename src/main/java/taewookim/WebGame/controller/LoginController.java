package taewookim.WebGame.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import taewookim.WebGame.controller.dto.response.http.UserResponse;
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

    /*Response에 User를 그대로 반환하면 안됐었는데(암호화 된 비번이 노출되기 때문) ㅜㅜㅜ 죄송합니다.
    * 그래도 로그인되어있는 사람의 메모리에만 존재하기 때문에 그렇게 위험하진 않았을거에요!
    * */
    @GetMapping("/loginuser")
    public ResponseEntity<UserResponse> getLoginedUser(
            @SessionAttribute(name = "loginUser")User loginUser
            ) {
        return ResponseEntity.ok(UserResponse.fromUser(loginUser));
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            HttpServletRequest request
    ) throws HTTPApiException {
        return ResponseEntity.ok(loginproxyservice.login(username, password, request));
    }

    @PostMapping("/regist")
    public ResponseEntity<Boolean> register(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password
    ) throws HTTPApiException {
        return ResponseEntity.ok(loginproxyservice.regist(username, password));
    }

}
