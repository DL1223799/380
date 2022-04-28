package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserCommentRepository;
import hkmu.comps380f.dao.CourseUserOptionRepository;
import hkmu.comps380f.dao.CourseUserRepository;
import hkmu.comps380f.dao.PollingRepository;
import hkmu.comps380f.exception.CourseCommentNotFound;
import hkmu.comps380f.exception.PollingNotFound;
import hkmu.comps380f.model.CommentForm;
import hkmu.comps380f.model.CourseUser;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.model.CourseUserComment;
import hkmu.comps380f.model.CourseUserOption;
import hkmu.comps380f.model.CourseUserPolling;
import hkmu.comps380f.model.OptionForm;
import hkmu.comps380f.model.PollingForm;
import hkmu.comps380f.service.CourseService;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user")
public class CourseUserController {

    @Resource
    private CourseService courseService;
    @Resource
    CourseUserRepository courseUserRepo;
    @Resource
    private CourseUserCommentRepository courseUserCommentRepository;
    @Resource
    private PollingRepository pollingRepository;
    @Resource
    private CourseUserOptionRepository courseUserOptionRepository;
    @Resource
    private CourseRepository courseRepository;

    private final Logger logger = LogManager.getLogger(this.getClass());

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
        model.addAttribute("courseUsers", courseUserRepo.findAll());
        return "listUser";
    }

    public static class Form {

        private String username;
        private String password;
        private String fullName;
        private String phoneNumber;
        private String deliveryAddress;
        private String[] roles;

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public String getFullName() {
            return fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        // ... getters and setters for each of the properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "courseUser", new Form());
    }

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        CourseUser user = new CourseUser(form.getUsername(), form.getPassword(),
                form.getFullName(), form.getPhoneNumber(), form.getDeliveryAddress(), form.getRoles());
        courseUserRepo.save(user);
        logger.info("User " + form.getUsername() + " created.");

        return new RedirectView("/user/list", true);
    }

    @GetMapping("/delete")
    public View delete(@PathVariable("username") String username) {
        courseUserRepo.delete(courseUserRepo.findById(username).orElse(null));
        logger.info("User " + username + " deleted.");
        return new RedirectView("/user/list", true);
    }

//    @GetMapping("/delete/{username}")
//    public View deleteCourse(@PathVariable("username") String username) {
//        courseUserRepo.delete(courseUserRepo.findById(username).orElse(null));
//        return new RedirectView("/user/list", true);
//    }
    @GetMapping("/delete/{courseId}")
    public View deleteCourse(@PathVariable("courseId") String courseId) {
        courseUserRepo.delete(courseUserRepo.findById(courseId).orElse(null));
        return new RedirectView("/user/list", true);
    }

    @PostMapping("/{courseId}/addComment")
    public String addComment(@PathVariable("courseId") long courseId,
            CommentForm commentForm, Principal principal, ModelMap model) {
        Course course = courseRepository.findById(courseId).orElse(null);
        CourseUser user = courseUserRepo.findById(principal.getName()).orElse(null);
        course.setComments(courseUserCommentRepository.findByCourseId(courseId));

        CourseUserComment courseUserComment = new CourseUserComment();
        courseUserComment.setComment(commentForm.getComment());
        courseUserComment.setCourse(course);
        courseUserComment.setUser(user);

        course.addComment(courseUserComment);

        courseRepository.save(course);
        return "redirect:/course/view/" + courseId;
    }

    @GetMapping("/delete/{courseId}/Comment/{commentId}")
    public String deleteComment(@PathVariable("courseId") long courseId,
            @PathVariable("commentId") long commentId) {
        try {
            courseService.deleteCourseComment(courseId, commentId);
        } catch (CourseCommentNotFound ex) {
        }
        return "redirect:/course/view/" + courseId;
    }

    @PostMapping("/{courseId}/addPolling")
    public String addPolling(@PathVariable("courseId") long courseId,
            PollingForm pollingForm, Principal principal, ModelMap model) {
        Course course = courseRepository.findById(courseId).orElse(null);
        CourseUser user = courseUserRepo.findById(principal.getName()).orElse(null);
        course.setPollings(pollingRepository.findByCourseId(courseId));

        CourseUserPolling courseUserPolling = new CourseUserPolling();
        courseUserPolling.setQuestion(pollingForm.getQuestion());
        courseUserPolling.setA(pollingForm.getA());
        courseUserPolling.setB(pollingForm.getB());
        courseUserPolling.setC(pollingForm.getC());
        courseUserPolling.setD(pollingForm.getD());
        courseUserPolling.setCourse(course);
        courseUserPolling.setUser(user);

        course.addPolling(courseUserPolling);

        courseRepository.save(course);
        return "redirect:/course/view/" + courseId;
    }

    @GetMapping("/delete/{courseId}/Polling/{pollingId}")
    public String deletePolling(@PathVariable("courseId") long courseId,
            @PathVariable("pollingId") long pollingId) {
        try {
            courseService.deleteCoursePolling(courseId, pollingId);
        } catch (PollingNotFound ex) {
        }
        return "redirect:/course/view/" + courseId;
    }

    @PostMapping("/{courseId}/{pollingId}/addOption")
    public String addOption(@PathVariable("courseId") long courseId, @PathVariable("pollingId") long pollingId,
            OptionForm optionForm, Principal principal, ModelMap model) {
        Course course = courseRepository.findById(courseId).orElse(null);
        CourseUser user = courseUserRepo.findById(principal.getName()).orElse(null);
        course.setOptions(courseUserOptionRepository.findByCourseId(courseId));
        List<CourseUserOption> options = courseUserOptionRepository.findAll();
        if (checkvote(pollingId, course.getLectureName())) {
            CourseUserPolling courseUserPolling = pollingRepository.findById(pollingId).orElse(null);
            CourseUserOption courseUserOption = new CourseUserOption();
            courseUserOption.setPolling(courseUserPolling);
            courseUserOption.setOption(optionForm.getOption());
            courseUserOption.setPollingId(pollingId);
            courseUserOption.setCourse(course);
            courseUserOption.setUser(user);
            course.addOption(courseUserOption);
        } else {CourseUserOption courseUserOption = new CourseUserOption();
            logger.info("Done");
            courseUserOption.setOption(checkOption(pollingId, principal.getName()));}
        courseRepository.save(course);

        return "redirect:/course/view/" + courseId;
    }

    /*@PostMapping("/edit/{courseId}/Polling/{pollingId}")
    public String editPolling(@PathVariable("courseId") long courseId, @PathVariable("pollingId") long pollingId,
            OptionForm optionForm, Principal principal) {
        List<CourseUserOption> option = courseUserOptionRepository.findByPollingId(pollingId);
        CourseUserOption courseUserOption = new CourseUserOption();
        String optionStr = option.get((int) pollingId).toString();
        courseUserOption.setOption(optionStr);

        return "redirect/course/view/" + courseId;
    }*/

    public boolean checkvote(long pollingId, String username) {
        List<CourseUserOption> options = courseUserOptionRepository.findAll();
        if (options.isEmpty()) {
            return true;
        } else {
            for (int i = 0; i < options.size(); i++) { 
            if (options.get(i).getUsername().equals(username) && (int) pollingId == (int) options.get(i).getPollingId()) { 
                
                return false; } }
        }
        return true;
    }

    public String checkOption(@PathVariable("pollingId") long pollingId, String username) {
        List<CourseUserOption> options = courseUserOptionRepository.findByPollingId(pollingId);
        String option = "";
        for (int i = 0; i < options.size(); i++) { 
            if (options.get(i).getUsername().equals(username)) {
                option = options.get(i).getOption();
         }
        }
       return option;
      }
    }

