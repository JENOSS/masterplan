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

}