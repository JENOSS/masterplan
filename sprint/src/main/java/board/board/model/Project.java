package board.board.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="project")
@NoArgsConstructor
@Data
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int projectidx;

    @Column(nullable=false)
    private String creatorid;

    @Column(nullable=false)
    private String title;

    private int year;

    private int month;

    private int date;

}
