package board.board.repository;

import board.board.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT DISTINCT user FROM User user WHERE user.username LIKE %:username%")
    Collection<User> findByName(@Param("username") String username);
}