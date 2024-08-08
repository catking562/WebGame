package taewookim.WebGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import taewookim.WebGame.entity.Score;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s ORDER BY s.score DESC LIMIT 5")
    List<Score> findTop5(); //인텔리제이 작명센스가 저보다 좋습니다.

}
