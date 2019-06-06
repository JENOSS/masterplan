package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import board.board.model.Sprint;
import board.board.repository.SprintRepository;
import board.board.service.ProjectService;
import board.board.service.SprintService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.model.Board;
import board.board.model.BoardFile;
import board.board.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private SprintRepository sprintRepository;

    @RequestMapping(value="/project/{projectidx}/board", method=RequestMethod.GET)
    public ModelAndView openBoardList(@PathVariable("projectidx") int projectidx) throws Exception{
        ModelAndView mv = new ModelAndView("boardList");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        mv.addObject("username",username);

        List<Board> list = boardService.selectBoardList(projectidx);
        mv.addObject("list", list);

        //전체 프로그레스바
        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해

        //to-do done 바

        mv.addObject("backlog_progress",backlog_progress);

        mv.addObject("backlog_done",backlog_done);


        mv.addObject("projectidx",projectidx);
        return mv;
    }

    @RequestMapping(value="/project/{projectidx}/board/write", method=RequestMethod.GET)
    public ModelAndView openBoardWrite(@PathVariable("projectidx") int projectidx) throws Exception{
        ModelAndView mv = new ModelAndView("boardWrite");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        mv.addObject("username",username);
        mv.addObject(projectidx);

        //전체 프로그레스바

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해


        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);

        mv.addObject("projectidx",projectidx);
        return mv;
    }

    @RequestMapping(value="/project/{projectidx}/board/write", method=RequestMethod.POST)
        public ModelAndView writeBoard(@PathVariable("projectidx") int projectidx, Board board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

        ModelAndView mv = new ModelAndView("redirect:/project/{projectidx}/board");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        mv.addObject("username",username);
        mv.addObject(projectidx);

        //전체 프로그레스바

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해


        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);

        boardService.saveBoard(board, multipartHttpServletRequest);

        mv.addObject("projectidx",projectidx);

        return mv;
    }

    @RequestMapping(value="/project/{projectidx}/board/{boardidx}", method=RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("projectidx") int projectidx, @PathVariable("boardidx") int boardidx) throws Exception{
        ModelAndView mv = new ModelAndView("boardDetail");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        mv.addObject("username",username);

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해


        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);


        Board board = boardService.selectBoardDetail(boardidx);
        mv.addObject("board", board);
        mv.addObject("projectidx",projectidx);
        return mv;
    }

    @RequestMapping(value="/project/{projectidx}/board/{boardidx}/update", method=RequestMethod.GET)
    public ModelAndView openBoardDetail_update(@PathVariable("projectidx") int projectidx, @PathVariable("boardidx") int boardidx) throws Exception{
        ModelAndView mv = new ModelAndView("boardDetail_modify");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        mv.addObject("username",username);

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해


        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);

        Board board = boardService.selectBoardDetail(boardidx);
        mv.addObject("board", board);
        mv.addObject("projectidx",projectidx);
        return mv;
    }

    @RequestMapping(value="/project/{projectidx}/board/{boardidx}/update", method=RequestMethod.PUT)
    public ModelAndView updateBoard(@PathVariable("projectidx") int projectidx,Board board) throws Exception{
        boardService.saveBoard(board,null);

        ModelAndView mv =new ModelAndView( "redirect:/project/{projectidx}/board");

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해


        mv.addObject("backlog_progress",backlog_progress);

        mv.addObject("backlog_done",backlog_done);

        mv.addObject("projectidx",projectidx);

        return mv;
    }

    @RequestMapping(value="/project/{projectidx}/board/{boardidx}", method=RequestMethod.DELETE)
    public ModelAndView deleteBoard(@PathVariable("boardidx") int boardidx, @PathVariable("projectidx") int projectidx) throws Exception{
        boardService.deleteBoard(boardidx);
        ModelAndView mv =new ModelAndView( "redirect:/project/{projectidx}/board");

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해


        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);

        mv.addObject("projectidx",projectidx);

        return mv;
    }

    @RequestMapping(value="/jpa/board/file", method=RequestMethod.GET)
    public void downloadBoardFile(int boardidx, int idx, HttpServletResponse response) throws Exception{
        BoardFile file = boardService.selectBoardFileInformation(idx, boardidx);
        System.out.println("file : "+file);
        byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredfilepath()));

        response.setContentType("application/octet-stream");
        response.setContentLength(files.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getOriginalfilename(),"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(files);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}