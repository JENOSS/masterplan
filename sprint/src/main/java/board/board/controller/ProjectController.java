package board.board.controller;

import board.board.model.*;
import board.board.repository.ProjectRepository;
import board.board.repository.SprintRepository;
import board.board.repository.SprintTodoRepository;
import board.board.service.ProjectService;
import board.board.service.SprintService;
import board.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.management.MalformedObjectNameException;
import java.util.*;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private SprintTodoRepository sprintTodoRepository;

    @Autowired
    private ProjectRepository projectRepository;



    @RequestMapping(value="/project", method= RequestMethod.GET)
    public ModelAndView openProjectList() throws Exception{
        ModelAndView mv = new ModelAndView("projectHome");

        List<ProjectMember> list = projectService.selectProjectMemberList();
        List<Project> plist = projectService.selectProjectList(list);

        User user = userService.selectUsername();

        System.out.println(list.size());
        System.out.println(plist.size());

        mv.addObject("list", plist);


        mv.addObject(user);
        return mv;
    }

    @RequestMapping(value="/project/write", method= RequestMethod.GET)
    public String openProjectWrite() throws Exception{

        return "projectWrite";
    }


    @RequestMapping(value="/project/write", method=RequestMethod.POST)
    public String writeBoard(Project project) throws Exception{

        projectService.saveProject(project);
        return "redirect:/project";
    }

    @RequestMapping(value="project/{projectidx}", method=RequestMethod.GET)
    public ModelAndView openProjectDetail(@PathVariable int projectidx) throws Exception{
        int check =0;
        check = projectService.pageCheck(projectidx);

        //전체 프로그레스바

        int backlog_all = projectService.progressBacklog(projectidx);

        int backlog_done = projectService.progressBacklog_done(projectidx);

        double temp = ((double)backlog_done/(double)backlog_all)*100;
        int backlog_progress = (int)temp; // 퍼센테이지로 표현하기 위해

        //to-do done 파이

        Sprint sprint = sprintRepository.findByProjectidx(projectidx);

        Long sprintid= sprint.getSprintid();

        int todo = sprintService.todo(sprintid);
        int done = sprintService.done(sprintid);
        int doing = sprintService.doing(sprintid);

        // sprint bar

        int cycle = sprint.getCycle();

        //경과일
        int pass = sprintService.dateCheck(sprint.getYear(),sprint.getMonth(),sprint.getDate());

        double temp2 = ((double)pass/(double)cycle)*100;

        int sprint_progress = (int)temp2;




        // leader progress bar


        List<String> projectMembers = projectService.selectProjectMemberListbyProjectidx(projectidx);
        Map<String,Integer> memberprogress =  new HashMap<>();
        Iterator<String> itr = projectMembers.iterator();
        while (itr.hasNext()) {
            String name = itr.next();
            int member_done=sprintService.donebyusername(sprintid,name);
            int member_all=sprintService.allsprinttodo(sprintid);
            double member_temp=((double)member_done/(double)member_all)*100;
            int member_progress=(int)member_temp;
            memberprogress.put(name,member_progress);
        }












        //start date


        Project pr = projectRepository.findByProjectidx(projectidx);
        String leader= pr.getCreatorid();

        String month="Feb";

        switch(pr.getMonth()){
            case 1:
                month="Jen";
                break;
            case 2:
                month="Feb";
                break;
            case 3:
                month="Mar";
                break;
            case 4:
                month="Apr";
                break;
            case 5:
                month="May";
                break;
            case 6:
                month="Jun";
                break;
            case 7:
                month="Jul";
                break;
            case 8:
                month="Aug";
                break;
            case 9:
                month="Sep";
                break;
            case 10:
                month="Oct";
                break;
            case 11:
                month="Nev";
                break;
            case 12:
                month="Dec";
                break;


        }



        String stratedate = pr.getDate()  + " " + month + " " + pr.getYear();


        //title

        String title = pr.getTitle();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<SprintTodo> doingList = sprintTodoRepository.findDoingBySprintid(sprintid);
        List<SprintTodo> doneList = sprintTodoRepository.findDoneBySprintid(sprintid);


        if(check == 1) {
            ModelAndView mv = new ModelAndView("index");
            mv.addObject(projectidx);
            mv.addObject("username",username);
            mv.addObject("backlog_progress",backlog_progress);
            mv.addObject("backlog_done",backlog_done);
            mv.addObject("backlog_all",backlog_all);
            mv.addObject("todo",todo);
            mv.addObject("done",done);
            mv.addObject("doing",doing);
            mv.addObject("sprint_progress",sprint_progress);
            mv.addObject("startdate",stratedate);
            mv.addObject("title", title);
            mv.addObject("leader",leader);
            mv.addObject("doingList",doingList);
            mv.addObject("doneList",doneList);
            //mv.addObject("leader_progress",leader_progress);

            //mv.addObject("leader_done",leader_done);
            //mv.addObject("leader_all",leader_all);
            mv.addObject("memberprogress",memberprogress);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView("welcome");
            return mv;
        }
    }

    @GetMapping("/project/{projectidx}/findProjectMember")
    public ModelAndView initFindForm(Map<String, Object> model,ModelAndView mv,@PathVariable int projectidx) {
        model.put("user", new User());
        mv.setViewName("findProjectMember");
        mv.addObject(model);
        mv.addObject(projectidx);
        return mv;
    }
    @GetMapping("/project/{projectidx}/projectMember")
    public ModelAndView processFindForm(User user, BindingResult result, Map<String, Object> model,ModelAndView mv,@PathVariable int projectidx) {

        // allow parameterless GET request for /owners to return all records
        if (user.getUsername() == null) {
            user.setUsername(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<User> results = this.userService.findByName(user.getUsername());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("username", "notFound", "not found");
            mv.setViewName("findProjectMember");
            return mv;
        } //else if (results.size() == 1) {
            // 1 owner found
            //user = results.iterator().next();
           // return "redirect:/owners/" + owner.getId();
        //}
        else {
            // multiple owners found
            model.put("selections", results);
            mv.addObject(model);
            mv.addObject(projectidx);
            mv.setViewName("projectmemberList");
            return mv;
        }
    }

    @GetMapping("/project/{projectidx}/{username}")
    public String addProjectMember(@PathVariable int projectidx,@PathVariable String username) {
        int check = projectService.isMember(projectidx,username);
        if(check == 0)
            projectService.addMember(projectidx,username);
        return "redirect:/project";
    }
}
