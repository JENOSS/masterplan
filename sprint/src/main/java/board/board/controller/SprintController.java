package board.board.controller;

import board.board.model.Sprint;
import board.board.model.SprintBacklog;
import board.board.model.SprintTodo;
import board.board.repository.SprintBacklogRepository;
import board.board.repository.SprintRepository;
import board.board.repository.SprintTodoRepository;
import board.board.service.ProjectService;
import board.board.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class SprintController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private SprintBacklogRepository sprintBacklogRepository;

    @Autowired
    private SprintTodoRepository sprinttodoRepository;

    @RequestMapping(value="/sprint/{projectidx}", method= RequestMethod.GET)
    public String sprint(@PathVariable int projectidx) throws Exception{

        ModelAndView mv = new ModelAndView();

        Sprint sprint = sprintRepository.findByProjectidx(projectidx);

        int level = sprint.getLevel();

        Long sprintid = sprint.getSprintid();

        switch (level){

            case 0:
                return "redirect:/sprint_start/"+sprintid;
            case 1:
                return "redirect:/sprint_backlog/"+sprintid;
            case 2:
                return "redirect:/sprint_plan/"+sprintid;
            case 3:
                return "redirect:/scrumboard/"+sprintid;
            case 4:
                return "redirect:/sprint_re/"+sprintid;
        }

        return "no";
    }

    @RequestMapping(value="/sprint_start/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprintstart_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_start");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Sprint sp = sprintRepository.findBySprintid(sprintid);
        int projectidx = sp.getProjectidx();

        //전체 프로그레스바

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해

        mv.addObject("projectidx",projectidx);
        mv.addObject("sprintid",sprintid);
        mv.addObject("username",username);
        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);
        return mv;
    }


    @RequestMapping(value="/sprint_backlog/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprintbacklog_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_backlog");
        mv.addObject("sprintid",sprintid);

        Sprint sp = sprintRepository.findBySprintid(sprintid);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        int projectidx = sp.getProjectidx();

        //전체 프로그레스바

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해


        mv.addObject("projectidx",projectidx);
        mv.addObject("username",username);
        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);



        if (sp.getLevel() < 1)
            sprintRepository.updateLevelBySprintid(sprintid,1);


        return mv;
    }

    @RequestMapping(value = "/sprint_backlog/{sprintid}/save", method = RequestMethod.POST)
    @ResponseBody
    public String postBacklog(@RequestBody Map<String, ArrayList> backlog, @PathVariable Long sprintid) {
        ArrayList<String> ary= (backlog.get("result"));
        int size = ary.size();
        int i;
        for(i=0;i<size;i++) {
            SprintBacklog sb = new SprintBacklog();
            sb.setSprintid(sprintid);
            sb.setContents(ary.get(i));
            sb.setIsdoing("N");
            sb.setIsdone("N");
            sprintBacklogRepository.save(sb);
        }
        return "backlog 완료";
    }

        @RequestMapping(value="/sprint_plan/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprinplan_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_plan");
        mv.addObject("sprintid",sprintid);

        Sprint sp = sprintRepository.findBySprintid(sprintid);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        int projectidx = sp.getProjectidx();

        //전체 프로그레스바
            int backlog_all = projectService.progressBacklog(projectidx);

            int backlog_done = projectService.progressBacklog_done(projectidx);

            double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해

        mv.addObject("projectidx",projectidx);
        mv.addObject("username",username);
        mv.addObject("backlog_progress",backlog_progress);

        mv.addObject("backlog_done",backlog_done);

        if (sp.getLevel() < 2)
            sprintRepository.updateLevelBySprintid(sprintid,2);

        List<SprintBacklog> backlogs= sprintBacklogRepository.findBySprintid(sprintid);
        mv.addObject("backlogs",backlogs);
        return mv;
    }

    @RequestMapping(value = "/sprint_plan/{sprintid}/{cycle}/save", method = RequestMethod.POST)
    @ResponseBody
    public String postToDo(@RequestBody Map<String, ArrayList> todo, @PathVariable("sprintid") Long sprintid,@PathVariable("cycle") int cycle) {
        ArrayList<String> ary= (todo.get("result"));
        ArrayList<String> ary2= (todo.get("result2"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        int size = ary.size();
        int size2 = ary2.size();
        int i;
        Long backlogid;
        for(i=0;i<size;i++) {
            SprintTodo td = new SprintTodo();
            td.setSprintid(sprintid);
            td.setContents(ary.get(i));
            td.setCycle(cycle);
            td.setIsdoing("N");
            td.setIsdone("N");
            sprinttodoRepository.save(td);
        }
        for(i=0;i<size2;i++) {
            backlogid = Long.parseLong(ary2.get(i));
            sprintBacklogRepository.updateToDoing(backlogid);
        }

        sprintRepository.updateCycleBySprintid(sprintid, cycle);
        return "plan 완료";
    }


    @RequestMapping(value="/scrumboard/{sprintid}", method= RequestMethod.GET)
    public ModelAndView scrumboard_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprintScrumBoard");
        mv.addObject("sprintid",sprintid);

        Sprint sp = sprintRepository.findBySprintid(sprintid);
        int cycle = sp.getCycle();
        //경과일 = 스프린트 생성날짜- 현재 날짜
        int pass = sprintService.dateCheck(sp.getYear(),sp.getMonth(),sp.getDate());
        int diff = cycle-pass;
        int size=0;
        //설정된 주기
        mv.addObject("cycle",cycle);

        //경과일
        mv.addObject("pass",pass);

        //설정된 주기와 실제 경과일간 차이 (0이되면 sprint_re로 넘어가야됨)
        mv.addObject("diff",diff);


        if (sp.getLevel() < 3)
            sprintRepository.updateLevelBySprintid(sprintid,3);

        if(diff <= 0){
           mv.addObject("msg","스프린트 기간이 끝났습니다. 스프린트 회고로 넘어가 주세요 !");
        }

        List<SprintTodo> todolist = sprinttodoRepository.findBySprintid(sprintid);
        List<SprintTodo> todo = new ArrayList<>();
        List<SprintTodo> doing = new ArrayList<>();
        List<SprintTodo> done = new ArrayList<>();
        size = todolist.size();
        for(int i=0;i<size;i++){
            if((todolist.get(i).getIsdone()).equals("Y")) {
                done.add(todolist.get(i));
            }
            else if((todolist.get(i).getIsdoing()).equals("Y")) { //equal
                doing.add(todolist.get(i));
            }
            else {
                todo.add(todolist.get(i));
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        int projectidx = sp.getProjectidx();

        //전체 프로그레스바


        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해

        mv.addObject("username",username);
        mv.addObject("backlog_progress",backlog_progress);
        mv.addObject("backlog_done",backlog_done);
        mv.addObject("projectidx",projectidx);

        mv.addObject("todo",todo);
        mv.addObject("doing",doing);
        mv.addObject("done",done);
        return mv;
    }

    @RequestMapping(value = "/scrumboard/{sprintid}/save", method = RequestMethod.POST)
    @ResponseBody
    public String scrumboard_post(@RequestBody Map<String, ArrayList> todoList ,@PathVariable Long sprintid) throws Exception{
        int size=0;
        Long todoId;
        String isdoing;
        String isdone;
        String username;
        ArrayList<Map<String,Object>> ary = todoList.get("result");


        for(int i=0;i<ary.size();i++) {
            //SprintTodo td = new SprintTodo();
            todoId = Long.parseLong((String)ary.get(i).get("id"));
            isdoing = (String)ary.get(i).get("isdoing");
            isdone = (String)ary.get(i).get("isdone");
            username = (String)ary.get(i).get("username");
           // sprinttodoRepository.updateInquiry(todoId,isdoing,isdone);
           // td.setTodoid(todoId);
           // td.setIsdoing(isdoing);
          //  td.setIsdoing(isdone);
            //td.setSprintid(sprintid);
            sprinttodoRepository.updateInquiry(todoId,isdoing,isdone,username);
        }

        return "저장 완료";
    }

    @RequestMapping(value="/sprint_re/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprinpre_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_re");
        mv.addObject("sprintid",sprintid);

        Sprint sp = sprintRepository.findBySprintid(sprintid);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        int projectidx = sp.getProjectidx();

        //전체 프로그레스바

        int backlog_doing = projectService.progressBacklog_doing(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_doing/(double)backlog_done)*100;

        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해

        mv.addObject("projectidx",projectidx);
        mv.addObject("username",username);
       // mv.addObject("backlog_progress",backlog_progress);
       // mv.addObject("backlog_doing",backlog_doing);
       // mv.addObject("backlog_done",backlog_done);


        if (sp.getLevel() < 4)
            sprintRepository.updateLevelBySprintid(sprintid,4);

        return mv;
    }



}
