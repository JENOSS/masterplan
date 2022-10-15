
package board.board.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board_file")
@NoArgsConstructor
@Data
public class BoardFile {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idx;

    @Column(nullable=false)
    private String originalfilename;

    @Column(nullable=false)
    private String storedfilepath;

    @Column(nullable=false)
    private long filesize;

    @Column(nullable=false)
    private String creatorid;

    @Column(nullable=false)
    private LocalDateTime createddatetime = LocalDateTime.now();

    private String updaterid;

    private LocalDateTime updateddatetime;
}
