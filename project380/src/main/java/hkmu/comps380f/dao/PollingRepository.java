/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import hkmu.comps380f.model.CourseUserPolling;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface PollingRepository extends JpaRepository<CourseUserPolling, Long> {

    public List<CourseUserPolling> findByCourseId(Long foodId);
}