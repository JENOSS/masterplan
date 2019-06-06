package board.board.service;


import board.board.model.Sprint;
import board.board.model.SprintBacklog;
import board.board.model.SprintTodo;
import board.board.repository.SprintRepository;
import board.board.repository.SprintTodoRepository;
import board.common.CurrentDate;
import board.common.DateDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    SprintRepository sprintRepository;

    @Autowired
    SprintTodoRepository sprintTodoRepository;

    public int findSprintLevel(int pidx){

        Sprint sp = sprintRepository.findByProjectidx(pidx);

        return sp.getLevel();
    }

    public int dateCheck(int year, int month, int date) {

        CurrentDate currentDate = new CurrentDate();

        DateDiff dateDiff = new DateDiff();

        return dateDiff.GetDifferenceOfDate(currentDate.year(),currentDate.month(),currentDate.date(),year,month,date);


    }

    public int todo(Long sprintid) {

        int todo = 0;

        String equal="N";

        List<SprintTodo> a = sprintTodoRepository.findBySprintid(sprintid);

        SprintTodo st = new SprintTodo();

        Iterator<SprintTodo> spb = a.iterator();
        while (spb.hasNext()) {

            st = spb.next();

            if (st.getIsdone().equals(equal)) {
                if(st.getIsdoing().equals(equal))
                    todo++;
            }

        }

        return todo ;
    }

    public int done(Long sprintid) {

        int done = 0;

        String equal="Y";

        List<SprintTodo> a = sprintTodoRepository.findBySprintid(sprintid);

        Iterator<SprintTodo> spb = a.iterator();
        while (spb.hasNext()) {
            if (spb.next().getIsdone().equals(equal)) {
                done++;
            }

        }

        return done ;

    }
    public int doing(Long sprintid) {

        int doing = 0;

        String equal="Y";

        List<SprintTodo> a = sprintTodoRepository.findBySprintid(sprintid);

        Iterator<SprintTodo> spb = a.iterator();
        while (spb.hasNext()) {
            if (spb.next().getIsdoing().equals(equal)) {
                doing++;
            }

        }

        return doing ;

    }


    public int donebyusername(Long sprintid, String username) {

        int done = 0;

        String equal="Y";

        SprintTodo st;


        List<SprintTodo> a = sprintTodoRepository.findBySprintid(sprintid);

        Iterator<SprintTodo> spb = a.iterator();
        while (spb.hasNext()) {
            st=spb.next();
            if (st.getIsdone().equals(equal)) {
                if(st.getUsername().equals(username))
                    done++;

            }

        }

        return done ;

    }

    public int allsprinttodo(Long sprintid) {

        int all = 0;

        SprintTodo st;


        List<SprintTodo> a = sprintTodoRepository.findBySprintid(sprintid);

        Iterator<SprintTodo> spb = a.iterator();
        while (spb.hasNext()) {
            st=spb.next();
            all++;


        }

        return all ;

    }


}
