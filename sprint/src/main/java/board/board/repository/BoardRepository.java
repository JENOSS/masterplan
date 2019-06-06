package board.board.repository;

import java.util.List;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import board.board.model.Board;
import board.board.model.BoardFile;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {

    @Query("SELECT board FROM Board board WHERE projectidx = :projectidx")
    List<Board> findAllByProjectidxByOrderByBoardidxDesc(@Param("projectidx") int projectidx);
    @Query("SELECT file FROM BoardFile file WHERE idx= :idx AND boardidx = :boardidx")
    BoardFile findBoardFile(@Param("idx") int idx, @Param("boardidx") int boardidx);
/*
    @Modifying
    @Query(value = "INSERT INTO t_file(idx,boardidx,originalfilename) VALUES (:idx,:boardidx,:originalfilename)",nativeQuery = true)
    @Transactional
    void insertBoardFile(@Param("idx")int idx,@Param("boardidx")int boardidx,@Param("originalfilename")String originalfilename);
*/
}
