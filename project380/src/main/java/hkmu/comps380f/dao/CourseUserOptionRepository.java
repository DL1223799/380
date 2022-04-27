package hkmu.comps380f.dao;

import hkmu.comps380f.model.CourseUserComment;
import hkmu.comps380f.model.CourseUserOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseUserOptionRepository extends JpaRepository<CourseUserOption, Long> {

    public List<CourseUserOption> findByCourseId(Long id);
    public List<CourseUserOption> findByPollingId(Long id);
    

}