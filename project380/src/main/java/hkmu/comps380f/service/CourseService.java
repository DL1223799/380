package hkmu.comps380f.service;

import hkmu.comps380f.dao.AttachmentRepository;
import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.CourseNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Course;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CourseService {

    @Resource
    private CourseRepository courseRepo;

    @Resource
    private AttachmentRepository attachmentRepo;

    @Transactional
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }

    @Transactional
    public Course getCourse(long id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Transactional(rollbackFor = CourseNotFound.class)
    public void delete(long id) throws CourseNotFound {
        Course deletedCourse = courseRepo.findById(id).orElse(null);
        if (deletedCourse == null) {
            throw new CourseNotFound();
        }
        courseRepo.delete(deletedCourse);
    }

    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long courseId, String name) throws AttachmentNotFound {
        Course course = courseRepo.findById(courseId).orElse(null);
        for (Attachment attachment : course.getAttachments()) {
            if (attachment.getName().equals(name)) {
                course.deleteAttachment(attachment);
                courseRepo.save(course);
                return;
            }
        }
        throw new AttachmentNotFound();
    }

    @Transactional
    public long createCourse(String customerName, String subject,
            String body, List<MultipartFile> attachments) throws IOException {
        Course course = new Course();
        course.setCustomerName(customerName);
        course.setSubject(subject);
        course.setBody(body);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setCourse(course);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                course.getAttachments().add(attachment);
            }
        }
        Course savedCourse = courseRepo.save(course);
        return savedCourse.getId();
    }

    @Transactional(rollbackFor = CourseNotFound.class)
    public void updateCourse(long id, String subject,
            String body, List<MultipartFile> attachments)
            throws IOException, CourseNotFound {
        Course updatedCourse = courseRepo.findById(id).orElse(null);
        if (updatedCourse == null) {
            throw new CourseNotFound();
        }

        updatedCourse.setSubject(subject);
        updatedCourse.setBody(body);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setCourse(updatedCourse);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedCourse.getAttachments().add(attachment);
            }
        }
        courseRepo.save(updatedCourse);
    }
}
