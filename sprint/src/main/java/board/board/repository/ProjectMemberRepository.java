package board.board.repository;

        import java.util.Collection;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Optional;

        import board.board.model.ProjectMember;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import board.board.model.Project;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;


@Repository
public interface ProjectMemberRepository extends CrudRepository<ProjectMember, Integer> {
        @Query("SELECT pm.id FROM ProjectMember pm where pm.projectidx = :projectidx")
        List<String> findidByProjectidx(@Param("projectidx") int projectidx);

        @Query("SELECT pm FROM ProjectMember pm where pm.id = :id")
        List<ProjectMember> findById(@Param("id") String id);

        @Query("SELECT pm FROM ProjectMember pm where pm.id = :username and pm.projectidx = :projectidx")
        ProjectMember findByProjectidxByUsername(@Param("projectidx") int projectidx,@Param("username") String username);

}
