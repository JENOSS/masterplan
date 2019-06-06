package board.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name="t_projectmember")
@NoArgsConstructor
@Data
public class ProjectMember {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int pmid;


    @Column(nullable=false)
    private int projectidx;

    @Column(nullable=false)
    private String id;

    public int getPmid() {
        return pmid;
    }

    public void setPmid(int pmid) {
        this.pmid = pmid;
    }

    public int getProjectidx() {
        return projectidx;
    }

    public void setProjectidx(int projectidx) {
        this.projectidx = projectidx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
