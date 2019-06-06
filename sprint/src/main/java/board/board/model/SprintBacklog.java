package board.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sprint_backlog")
@NoArgsConstructor
@Data
public class SprintBacklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backlogid;

    private Long sprintid;

    private String contents;

    private String isdoing;

    private String isdone;

    public Long getBacklogid() {
        return backlogid;
    }

    public void setBacklogid(Long backlogid) {
        this.backlogid = backlogid;
    }

    public Long getSprintid() {
        return sprintid;
    }

    public void setSprintid(Long sprintid) {
        this.sprintid = sprintid;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getIsdoing() {
        return isdoing;
    }

    public void setIsdoing(String isdoing) {
        this.isdoing = isdoing;
    }

    public String getIsdone() {
        return isdone;
    }

    public void setIsdone(String isdone) {
        this.isdone = isdone;
    }
}