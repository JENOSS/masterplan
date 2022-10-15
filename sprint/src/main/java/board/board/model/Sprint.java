package board.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sprint")
@NoArgsConstructor
@Data
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintid;

    private int level;

    private int projectidx;

    private int cycle;

    private int year;

    private int month;

    private int date;

}
