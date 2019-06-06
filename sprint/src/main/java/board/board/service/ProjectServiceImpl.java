package board.board.service;
import board.board.model.Project;
import board.board.model.ProjectMember;
import board.board.model.Sprint;
import board.board.model.SprintBacklog;
import board.board.repository.ProjectMemberRepository;
import board.board.repository.ProjectRepository;
import board.board.repository.SprintBacklogRepository;
import board.board.repository.SprintRepository;
import board.common.CurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class ProjectServiceImpl implements   ProjectService{

    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SprintRepository sprintRepository;
    @Autowired
    SprintBacklogRepository sprintBacklogRepository;


   public List<ProjectMember> selectProjectMemberList() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String username = authentication.getName();
        return projectMemberRepository.findById(username);
    }

    public List<Project> selectProjectList(List<ProjectMember> list){
            int pidx;
        List<Project> plist = new ArrayList<>();
            for(ProjectMember pm : list){
                pidx = pm.getProjectidx();
                plist.add(projectRepository.findByProjectidx(pidx));
            }
        return plist;
    }

    public  void saveProject(Project project) {

        ProjectMember pm = new ProjectMember();

        CurrentDate currentDate = new CurrentDate();

        int projectidx;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        project.setCreatorid(username);
        project.setYear(currentDate.year());
        project.setMonth(currentDate.month());
        project.setDate(currentDate.date());
        projectRepository.save(project);

        projectidx = projectRepository.findMaxProjectidx();

        pm.setId(username);
        pm.setProjectidx(projectidx);
        projectMemberRepository.save(pm);

        Sprint sp = new Sprint();
        sp.setProjectidx(projectidx);
        sp.setLevel(0);
        sp.setCycle(0);



        sp.setYear(currentDate.year());
        sp.setMonth(currentDate.month());
        sp.setDate(currentDate.date());

        sprintRepository.save(sp);


    }

    public int pageCheck(int projectidx) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<String> pusername = projectMemberRepository.findidByProjectidx(projectidx);
        String name;
        Iterator<String> itr = pusername.iterator();
        while (itr.hasNext()) {
            name = itr.next();
            if (username.equals(name) == true) {
                return 1;
            }
        }
        return 0;
    }

    public int isMember(int projectidx,String username) {

        ProjectMember pm ;
        pm = projectMemberRepository.findByProjectidxByUsername(projectidx,username);
        if(pm == null){ return 0;}
        return 1;
    }

    public void addMember(int projectidx,String username) {
       ProjectMember pm = new ProjectMember();
       pm.setId(username);
       pm.setProjectidx(projectidx);
       projectMemberRepository.save(pm);
    }

    public int progressBacklog(int projectidx) {

        Long sprintid;

        Sprint sp = sprintRepository.findByProjectidx(projectidx);
        sprintid = sp.getSprintid();

        int all_backlog=0;

        List<SprintBacklog> a = sprintBacklogRepository.findBySprintid(sprintid);

        Iterator<SprintBacklog> spb = a.iterator();

        //has.next사용하면 안됨
        while (spb.hasNext()) {
            if(spb.next().getSprintid().equals(sprintid))
                all_backlog++;
        }



        return all_backlog;
    }

    public int progressBacklog_doing(int projectidx) {

        Long sprintid;

        Sprint sp = sprintRepository.findByProjectidx(projectidx);
        sprintid = sp.getSprintid();

        int doing = 0;

        String equal="Y";

        List<SprintBacklog> a = sprintBacklogRepository.findBySprintid(sprintid);

        Iterator<SprintBacklog> spb = a.iterator();
        while (spb.hasNext()) {
            if (spb.next().getIsdoing().equals(equal)) {
                doing++;
            }

        }

        return doing ;
    }

    public int progressBacklog_done(int projectidx) {

        Long sprintid;

        Sprint sp = sprintRepository.findByProjectidx(projectidx);
        sprintid = sp.getSprintid();

        int done = 0;

        String equal = "Y";
        List<SprintBacklog> a = sprintBacklogRepository.findBySprintid(sprintid);

        Iterator<SprintBacklog> spb = a.iterator();
        while (spb.hasNext()) {
            if (spb.next().getIsdone().equals(equal)) {
                done++;
            }
        }

        return done;
    }

    public List<String> selectProjectMemberListbyProjectidx(int projectidx) {
       return projectMemberRepository.findidByProjectidx(projectidx);
    }


}
