package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserCommentRepository;
import hkmu.comps380f.dao.CourseUserOptionRepository;
import hkmu.comps380f.dao.CourseUserRepository;
import hkmu.comps380f.dao.PollingRepository;
import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.CourseNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.CommentForm;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.model.CourseUser;
import hkmu.comps380f.model.CourseUserComment;
import hkmu.comps380f.model.CourseUserOption;
import hkmu.comps380f.model.CourseUserPolling;
import hkmu.comps380f.model.OptionForm;
import hkmu.comps380f.model.PollingForm;
import hkmu.comps380f.service.AttachmentService;
import hkmu.comps380f.service.CourseService;
import hkmu.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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
    CourseUserRepository courseUserRepo;
    @Resource
    private CourseUserCommentRepository courseUserCommentRepository;
    @Resource
    private CourseUserOptionRepository courseUserOptionRepository;
    @Resource
    private PollingRepository pollingRepository;
    @Autowired
    private CourseService courseService;

    @Autowired
    private AttachmentService attachmentService;

    // Controller methods, Form object, ...
    @GetMapping({"", "/list"})
    public String list(ModelMap model) {
        List<CourseUserComment> comments = courseUserCommentRepository.findAll();
        List<CourseUserPolling> pollings = pollingRepository.findAll();
        List<String> pollinginfos = new ArrayList<String>();
        for (int i = 0; i < pollings.size(); i++) {
            int pid = (int) pollings.get(i).getCourseId();
            String v = courseService.getCourses().get(pid - 1).getSubject();
            if (pollinginfos.indexOf(v) < 0) {
                pollinginfos.add(v);
            }

        }
        List<String> Commentinfos = new ArrayList<String>();
        for (int i = 0; i < comments.size(); i++) {
            int cid = (int) comments.get(i).getCourseId();
            String v = courseService.getCourses().get(cid - 1).getSubject();
            if (Commentinfos.indexOf(v) < 0) {
                Commentinfos.add(v);
            }

        }
        model.addAttribute("comments", comments);
        model.addAttribute("pollings", pollings);
        model.addAttribute("Commentinfos", Commentinfos);
        model.addAttribute("pollinginfos", pollinginfos);
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
        List<CourseUserComment> comments = courseUserCommentRepository.findAll();
        List<CourseUserPolling> pollings = pollingRepository.findAll();
        List<CourseUserOption> options = courseUserOptionRepository.findAll();
        List<Integer> numboptions = new ArrayList<Integer>();

        for (int i = 0; i < pollings.size(); i++) {
            int n = 0;
            for (int j = 0; j < options.size(); j++) {
                if (options.get(j).getPollingId() == pollings.get(i).getId()) {
                    n += 1;
                }
            }
            numboptions.add(n);
        }
        model.addAttribute("numboptions", numboptions);
        model.addAttribute("comments", comments);
        model.addAttribute("pollings", pollings);
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

    /**
     * @GetMapping("/polling/{courseId}") public String
     * polling(@PathVariable("courseId") long courseId, ModelMap model) { Course
     * course = courseService.getCourse(courseId); if (course == null) { return
     * "redirect:/course/view"; }
     * //course.setPollings(pollingRepository.findByCourseId(courseId));
     * //model.addAttribute("course", course);
     * //model.addAttribute("newPolling", new PollingForm()); return "view"; }*
     */
    @GetMapping("/polling/{courseId}")
    public String polling(@PathVariable("courseId") long courseId, ModelMap model) {
        //model.addAttribute("courseDatabase", courseService.getCourses());
        Course course = courseService.getCourse(courseId);
        if (course == null) {
            return "redirect:/course/view";
        }
        CourseUserPolling polling = new CourseUserPolling();
        model.addAttribute("courseDatabase", courseService.getCourses());
        model.addAttribute("course", course);
        model.addAttribute("polling", polling);
        model.addAttribute("newPolling", new PollingForm());
        return "polling";
    }

    @GetMapping("/option/{courseId}/{pollingId}")
    public String option(@PathVariable("courseId") long courseId, @PathVariable("pollingId") long pollingId,Principal principal, ModelMap model) {
        //model.addAttribute("courseDatabase", courseService.getCourses());
        Course course = courseService.getCourse(courseId);
        CourseUserPolling polling = pollingRepository.findById(pollingId).orElse(null);
        if (course == null) {
            return "redirect:/course/view";
        }
        List<String>voted=new ArrayList<String>();
        List vote=courseUserOptionRepository.findByPollingId(pollingId);

        CourseUserOption option = new CourseUserOption();
        model.addAttribute("courseDatabase", courseService.getCourses());
        model.addAttribute("course", course);
        model.addAttribute("polling", polling);
        model.addAttribute("pollingId", pollingId);
        model.addAttribute("option", option);
        model.addAttribute("newOption", new OptionForm());
        return "option";
    }

    @GetMapping("/commenthistory")
    public String commentHistory(Principal principal, ModelMap model) {
        List<CourseUserComment> comments = courseUserCommentRepository.findAll();
        CourseUser user = courseUserRepo.findById(principal.getName()).orElse(null);
        String username = user.getUsername();
        List<String> userComments = new ArrayList<String>();
        for (int i = 0; i < comments.size(); i++) {
            int cid = (int) comments.get(i).getCourseId();
            String v = courseService.getCourses().get(cid - 1).getSubject();
            if (username.equals(comments.get(i).getUsername())) {
                userComments.add("Course: " + v + ": " + comments.get(i).getComment());
            }

        }
        model.addAttribute("userComments", userComments);
        return "commenthistory";
    }

    @GetMapping("/votehistory/{pollingId}")
    public String voteHistory(Principal principal, ModelMap model,@PathVariable("pollingId") long pollingId) {
        List<CourseUserOption> votes = courseUserOptionRepository.findByPollingId(pollingId);
        CourseUser user = courseUserRepo.findById(principal.getName()).orElse(null);
        String username = user.getUsername();
        List<String> userPollings = new ArrayList<String>();
        for (int i = 0; i < votes.size(); i++) {
//            int cid = (int) votes.get(i).getPollingId();
            String v = votes.get(i).getCourse().getSubject();
//            String v = courseService.getCourses().get(cid - 1).getSubject();
            if (username.equals(votes.get(i).getUsername())) {
                userPollings.add("Vote title: " + v + ": " + votes.get(i).getCourseId() +" Voter : "+ votes.get(i).getUsername() + " Voted to :"+votes.get(i).getOption());
            }

        }
        model.addAttribute("userPollings", userPollings);
        return "votehistory";
    }
}
