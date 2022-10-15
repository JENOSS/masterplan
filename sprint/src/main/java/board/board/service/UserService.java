package board.board.service;

import board.board.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UserService extends UserDetailsService {
    void save(User user);

    User findByUsername(String username);

    User selectUsername();

    Collection<User> findByName(String username);
}
