/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.model;

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
@Table(name = "Polling_option")
public class CourseUserOption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private long id;
    
    @Column(name="username", insertable=false, updatable=false)
    private String username;
    
    @Column(name="course_id", insertable=false, updatable=false)
    private long courseId;
    
    @Column(name="opt")
    private String option;

    @Column(name="polling_id")
    private String pollingId;
    
    @ManyToOne
    @JoinColumn(name="username")
    private CourseUser user;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="polling_id")
    private CourseUserPolling polling;  

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getOption() {
        return option;
    }

    public String getPollingId() {
        return pollingId;
    }

    public CourseUser getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public CourseUserPolling getPolling() {
        return polling;
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

    public void setOption(String option) {
        this.option = option;
    }

    public void setPollingId(String pollingId) {
        this.pollingId = pollingId;
    }

    public void setUser(CourseUser user) {
        this.user = user;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setPolling(CourseUserPolling polling) {
        this.polling = polling;
    }
  
}


