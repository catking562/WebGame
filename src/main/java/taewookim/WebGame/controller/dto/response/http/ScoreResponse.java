package taewookim.WebGame.controller.dto.response.http;

import taewookim.WebGame.entity.Score;

public record ScoreResponse(String username, int score) {

    public static ScoreResponse fromScore(Score score) {
        return new ScoreResponse(score.getUser().getUserName(), score.getScore());
    }

}
