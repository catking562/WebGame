package taewookim.WebGame.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import taewookim.WebGame.controller.dto.response.http.ScoreListResponse;
import taewookim.WebGame.controller.dto.response.http.ScoreResponse;
import taewookim.WebGame.exception.HTTPApiException;
import taewookim.WebGame.proxyservice.ScoreProxyService;

@RestController
public class ScoreController {

    private final ScoreProxyService scoreservice;

    public ScoreController(ScoreProxyService scoreservice) {
        this.scoreservice = scoreservice;
    }

    @GetMapping("/score")
    public ResponseEntity<ScoreResponse> getScore(@RequestParam Long userId) throws HTTPApiException {
        return ResponseEntity.ok(ScoreResponse.fromScore(scoreservice.getScoreByUser(userId)));
    }

    @GetMapping("/scores")
    public ResponseEntity<ScoreListResponse> getScoreList() {
        return ResponseEntity.ok(ScoreListResponse.fromList(scoreservice.getScoreList()));
    }

}
