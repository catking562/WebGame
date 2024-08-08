package taewookim.WebGame.proxyservice;

import org.springframework.stereotype.Service;
import taewookim.WebGame.entity.Score;
import taewookim.WebGame.exception.ExceptionType;
import taewookim.WebGame.exception.HTTPApiException;
import taewookim.WebGame.service.ScoreService;
import taewookim.WebGame.service.UserService;

import java.util.List;

@Service
public class ScoreProxyService {

    private final ScoreService scoreService;
    private final UserService userService;

    public ScoreProxyService(ScoreService scoreService, UserService userService) {
        this.scoreService = scoreService;
        this.userService = userService;
    }

    public Score getScoreByUser(long userid) throws HTTPApiException {
        if(!userService.isExistId(userid)) throw new HTTPApiException(ExceptionType.User_NotFound);
        return scoreService.getScoreByUser(userid);
    }

    public List<Score> getScoreList() {
        return scoreService.getScoreList();
    }

}
