package board.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sprint_todo")
@NoArgsConstructor
@Data
public class  SprintTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoid;

    private Long sprintid;

    private String contents;

    private String isdoing;

    private String isdone;

    private String username;

    private int cycle;

    public int getCycle() { return cycle; }

    public void setCycle(int cycle) { this.cycle = cycle; }

    public Long getTodoid() {
        return todoid;
    }

    public void setTodoid(Long todoid) {
        this.todoid = todoid;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}