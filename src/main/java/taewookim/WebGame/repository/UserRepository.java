package taewookim.WebGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import taewookim.WebGame.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :name AND u.password = :pw")
    User getUserWithLogin(String name, String pw);

    @Query("SELECT u FROM User u WHERE u.username = :name")
    User getUserByName(String name);

}
