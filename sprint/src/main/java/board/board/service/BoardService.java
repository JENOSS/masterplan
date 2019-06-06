package board.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.model.Board;
import board.board.model.BoardFile;

public interface BoardService {

    List<Board> selectBoardList(int projectidx) throws  Exception;

    void updateBoard(Board board) throws Exception;

    void saveBoard(Board board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    Board selectBoardDetail(int boardidx) throws Exception;

    void deleteBoard(int boardidx) throws Exception;

    BoardFile selectBoardFileInformation(int idx,int boardidx) throws Exception;
}
