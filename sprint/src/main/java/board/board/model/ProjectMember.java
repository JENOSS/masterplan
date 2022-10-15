package board.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name="project_member")
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

}
