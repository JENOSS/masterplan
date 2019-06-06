package board.board.service;


public interface SprintService {

    int findSprintLevel(int pidx) throws Exception;

    int dateCheck(int year, int month, int date) throws Exception;

    int todo(Long sprintid);

    int done(Long springid);

    int doing(Long springid);

    int donebyusername(Long sprintid , String username);

    int allsprinttodo(Long sprintid);
}
