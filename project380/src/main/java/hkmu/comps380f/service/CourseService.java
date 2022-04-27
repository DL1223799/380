package hkmu.comps380f.service;

import hkmu.comps380f.dao.AttachmentRepository;
import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserCommentRepository;
import hkmu.comps380f.dao.CourseUserOptionRepository;
import hkmu.comps380f.dao.PollingRepository;
import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.CourseCommentNotFound;
import hkmu.comps380f.exception.CourseNotFound;
import hkmu.comps380f.exception.OptionNotFound;
import hkmu.comps380f.exception.PollingNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.model.CourseUserComment;
import hkmu.comps380f.model.CourseUserOption;
import hkmu.comps380f.model.CourseUserPolling;
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
    @Resource
    private CourseUserCommentRepository courseUserCommentRepository;
    @Resource
    private PollingRepository pollingRepository;
    @Resource
    private CourseUserOptionRepository courseUserOptionRepository;
    
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
    public long createCourse(String lectureName, String subject,
            String body, List<MultipartFile> attachments) throws IOException {
        Course course = new Course();
        course.setLectureName(lectureName);
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
    
    @Transactional(rollbackFor = CourseCommentNotFound.class)
    public void deleteCourseComment(long courseId, long commentId) throws CourseCommentNotFound {
        Course course = courseRepo.findById(courseId).orElse(null);
        course.setComments(courseUserCommentRepository.findByCourseId(courseId));
        
        for (CourseUserComment courseUserComment : course.getComments()) {
            if (courseUserComment.getId() == commentId) {
                course.deleteComment(courseUserComment);
                courseRepo.save(course);
                return;
            }
        }
        throw new CourseCommentNotFound();
    }
    
    @Transactional(rollbackFor = PollingNotFound.class)
    public void deleteCoursePolling(long courseId, long pollingId) throws PollingNotFound {
        Course course = courseRepo.findById(courseId).orElse(null);
        course.setPollings(pollingRepository.findByCourseId(courseId));
        
        for (CourseUserPolling courseUserPolling : course.getPollings()) {
            if (courseUserPolling.getId() == pollingId) {
                List<CourseUserOption> options = courseUserOptionRepository.findByPollingId(pollingId);
                for (CourseUserOption option : options) {
                    course.deleteOption(option);
                }
                course.deletePolling(courseUserPolling);
                courseRepo.save(course);
                return;
            }
        }
        throw new PollingNotFound();
    }
    
    @Transactional(rollbackFor = OptionNotFound.class)
    public void deleteCourseOption(long courseId, long optionId) throws OptionNotFound {
        Course course = courseRepo.findById(courseId).orElse(null);
        course.setOptions(courseUserOptionRepository.findByCourseId(courseId));
        
        for (CourseUserOption courseUserOption : course.getOptions()) {
            if (courseUserOption.getId() == optionId) {
                course.deleteOption(courseUserOption);
                courseRepo.save(course);
                return;
            }
        }
        throw new OptionNotFound();
    }
}
