package board.board.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board")
@NoArgsConstructor
@Data
public class Board {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int boardidx;

    @Column(nullable=false)
    private int projectidx;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String contents;

    @Column(nullable=false)
    private int hitcnt = 0;

    @Column(nullable=false)
    private String creatorid;

    @Column(nullable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createddatetime = LocalDateTime.now();

    private String updaterid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateddatetime;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="boardidx")
    private Collection<BoardFile> filelist;

}
