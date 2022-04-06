/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.model;

/**
 *
 * @author user
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Course_user_Comments")
public class CourseUserComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;
    
    @Column(name="username", insertable=false, updatable=false)
    private String username;
    
    @Column(name="course_id", insertable=false, updatable=false)
    private long courseId;
    
    @Column(name="comment")
    private String comment;
    
    @ManyToOne
    @JoinColumn(name="username")
    private CourseUser user;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    public long getId() {
        return id;
    }

    public void setId(int commentId) {
        this.id = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CourseUser getUser() {
        return user;
    }

    public void setUser(CourseUser user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    
}

