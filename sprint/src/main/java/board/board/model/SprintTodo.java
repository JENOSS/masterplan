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

}