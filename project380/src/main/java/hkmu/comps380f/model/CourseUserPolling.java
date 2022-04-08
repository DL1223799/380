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
@Table(name = "Course_Polling")
public class CourseUserPolling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "polling_id")
    private long id;
    @Column(name="course_id", insertable=false, updatable=false)
    private long courseId;
    
    @Column(name="question")
    private String question;
    @Column(name="a")
    private String a;
    @Column(name="b")
    private String b;
    @Column(name="c")
    private String c;
    @Column(name="d")
    private String d;
        
    @Column(name="username", insertable=false, updatable=false)
    private String username;

    @ManyToOne
    @JoinColumn(name="username")
    private CourseUser user;
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getQuestion() {
        return question;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public CourseUser getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setB(String b) {
        this.b = b;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setUser(CourseUser user) {
        this.user = user;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
}

