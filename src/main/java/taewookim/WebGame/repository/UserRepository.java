package taewookim.WebGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import taewookim.WebGame.entity.User;

import java.util.Objects;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :name AND u.password = :pw")
    User getUserWithLogin(String name, String pw);

    @Query("SELECT u FROM User u WHERE u.username = :name")
    User getUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.id = :userid")
    User getUserById(Long userid);

    default Boolean isExistUser(Long userid) {
        try{
            return Objects.equals(getUserById(userid).getId(), userid);
        }catch(Exception e){
            return false;
        }
    }

}
