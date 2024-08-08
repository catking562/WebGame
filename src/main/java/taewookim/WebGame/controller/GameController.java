package taewookim.WebGame.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import taewookim.WebGame.controller.dto.request.http.UserRequest;
import taewookim.WebGame.entity.User;
import taewookim.WebGame.proxyservice.UserProxyService;
import taewookim.WebGame.repository.UserRepository;
import taewookim.WebGame.service.UserService;

@RestController
public class GameController {

    private final UserProxyService userService;

    public GameController(UserProxyService userService) {
        this.userService = userService;
    }

    @PostMapping("/startmatching")
    public Boolean startMatch(
            @SessionAttribute(name = "loginUser", required = false) User loginUser,
            @RequestParam Long id,
            @RequestParam String username
    ) {
        //자바스크립트에서 body에 UserRequest를 넣어보낼려 했지만 실패...
        return userService.isSame(loginUser, new UserRequest(id, username));
    }

}
