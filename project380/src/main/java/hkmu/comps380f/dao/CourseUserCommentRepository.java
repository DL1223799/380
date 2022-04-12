/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import hkmu.comps380f.model.CourseUserComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseUserCommentRepository extends JpaRepository<CourseUserComment, Long> {

    public List<CourseUserComment> findByCourseId(Long commentId);
    
    

}