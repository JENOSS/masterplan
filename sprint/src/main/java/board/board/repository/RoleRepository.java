package board.board.repository;

import board.board.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>{
    List<Role> findById(long Id);
}
