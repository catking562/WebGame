package taewookim.WebGame.controller.dto.response.http;

import taewookim.WebGame.entity.Score;

import java.util.ArrayList;
import java.util.List;

public record ScoreListResponse(
        List<ScoreResponse> list
) {

    public static ScoreListResponse fromList(List<Score> scorelist) {
        ArrayList<ScoreResponse> list = new ArrayList<>();
        for(Score score : scorelist) {
            list.add(ScoreResponse.fromScore(score));
        }
        return new ScoreListResponse(list);
    }

}
