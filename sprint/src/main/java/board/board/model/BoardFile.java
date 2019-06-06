
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
@Table(name="t_jpa_file")
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

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getOriginalfilename() {
        return originalfilename;
    }

    public void setOriginalfilename(String originalfilename) {
        this.originalfilename = originalfilename;
    }

    public String getStoredfilepath() {
        return storedfilepath;
    }

    public void setStoredfilepath(String storedfilepath) {
        this.storedfilepath = storedfilepath;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public LocalDateTime getCreateddatetime() {
        return createddatetime;
    }

    public void setCreateddatetime(LocalDateTime createddatetime) {
        this.createddatetime = createddatetime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid;
    }

    public LocalDateTime getUpdateddatetime() {
        return updateddatetime;
    }

    public void setUpdateddatetime(LocalDateTime updateddatetime) {
        this.updateddatetime = updateddatetime;
    }
}
