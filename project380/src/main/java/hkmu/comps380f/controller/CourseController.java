package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserCommentRepository;
import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.CourseNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.CommentForm;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.service.AttachmentService;
import hkmu.comps380f.service.CourseService;
import hkmu.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/course")
public class CourseController {
@Resource
    private CourseRepository courseRepository;

    @Resource
    private CourseUserCommentRepository courseUserCommentRepository;
    @Autowired
    private CourseService courseService;

    @Autowired
    private AttachmentService attachmentService;

    // Controller methods, Form object, ...
    @GetMapping({"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("courseDatabase", courseService.getCourses());
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "courseForm", new Form());
    }

    public static class Form {

        private String subject;
        private String body;
        private List<MultipartFile> attachments;

        // Getters and Setters of subject, body, attachments
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

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @PostMapping("/create")
    public String create(Form form, Principal principal) throws IOException {
        long courseId = courseService.createCourse(principal.getName(),
                form.getSubject(), form.getBody(), form.getAttachments());
        return "redirect:/course/view/" + courseId;
    }

    @GetMapping("/view/{courseId}")
    public String view(@PathVariable("courseId") long courseId, ModelMap model) {
        Course course = courseService.getCourse(courseId);
        if (course == null) {
            return "redirect:/course/list";
        }
        course.setComments(courseUserCommentRepository.findByCourseId(courseId));
        model.addAttribute("course", course);
        model.addAttribute("newComment", new CommentForm());
        return "view";
    }

    @GetMapping("/{courseId}/attachment/{attachment:.+}")
    public View download(@PathVariable("courseId") long courseId,
            @PathVariable("attachment") String name) {

        Attachment attachment = attachmentService.getAttachment(courseId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/course/list", true);
    }

    @GetMapping("/{courseId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("courseId") long courseId,
            @PathVariable("attachment") String name) throws AttachmentNotFound {
        courseService.deleteAttachment(courseId, name);
        return "redirect:/course/edit/" + courseId;
    }

    @GetMapping("/edit/{courseId}")
    public ModelAndView showEdit(@PathVariable("courseId") long courseId,
            Principal principal, HttpServletRequest request) {
        Course course = courseService.getCourse(courseId);
        if (course == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(course.getLectureName()))) {
            return new ModelAndView(new RedirectView("/course/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("course", course);

        Form courseForm = new Form();
        courseForm.setSubject(course.getSubject());
        courseForm.setBody(course.getBody());
        modelAndView.addObject("courseForm", courseForm);

        return modelAndView;
    }

    @PostMapping("/edit/{courseId}")
    public String edit(@PathVariable("courseId") long courseId, Form form,
            Principal principal, HttpServletRequest request)
            throws IOException, CourseNotFound {
        Course course = courseService.getCourse(courseId);
        if (course == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(course.getLectureName()))) {
            return "redirect:/course/list";
        }

        courseService.updateCourse(courseId, form.getSubject(),
                form.getBody(), form.getAttachments());
        return "redirect:/course/view/" + courseId;
    }

    @GetMapping("/delete/{courseId}")
    public String deleteCourse(@PathVariable("courseId") long courseId)
            throws CourseNotFound {
        courseService.delete(courseId);
        return "redirect:/course/list";
    }

}