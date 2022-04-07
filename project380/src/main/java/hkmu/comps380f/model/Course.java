package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String lectureName;

    private String subject;

    private String body;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<CourseUserComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<CourseUserPolling> pollings = new ArrayList<>();

    public void setPollings(List<CourseUserPolling> pollings) {
        this.pollings = pollings;
    }

    public List<CourseUserPolling> getPollings() {
        return pollings;
    }

    public void setComments(List<CourseUserComment> comments) {
        this.comments = comments;
    }

    public List<CourseUserComment> getComments() {
        return comments;
    }
    // getters and setters of all properties
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void deleteAttachment(Attachment attachment) {
        attachment.setCourse(null);
        this.attachments.remove(attachment);
    }
    public void addComment(CourseUserComment comment) {
        comments.add(comment);
    }
    public void deleteComment(CourseUserComment comment) {
        comment.setCourse(null);
        comment.setUser(null);
        comments.remove(comment);
    }
    public void addPolling(CourseUserPolling polling) {
        pollings.add(polling);
    }
    public void deletePolling(CourseUserPolling polling) {
        polling.setCourse(null);
        polling.setUser(null);
        pollings.remove(polling);
    }
}
