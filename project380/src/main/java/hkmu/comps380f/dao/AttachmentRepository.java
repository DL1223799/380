package hkmu.comps380f.dao;

import hkmu.comps380f.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    public Attachment findByCourseIdAndName(long courseId, String name);
}
