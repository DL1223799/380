package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserCommentRepository;
import hkmu.comps380f.dao.CourseUserOptionRepository;
import hkmu.comps380f.dao.CourseUserRepository;
import hkmu.comps380f.dao.PollingRepository;
import hkmu.comps380f.exception.CourseCommentNotFound;
import hkmu.comps380f.exception.OptionNotFound;
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
import hkmu.comps380f.validator.UserValidator;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    private UserValidator userValidator;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping({"", "/list"})
public String list(ModelMap model) {
        List<CourseUserComment> comments = courseUserCommentRepository.findAll();
        List<CourseUserPolling> pollings = pollingRepository.findAll();
        List<String> pollinginfos = new ArrayList<String>();
        List<Integer> Pcids = new ArrayList<Integer>();
        List<Integer> Pids = new ArrayList<Integer>();
        for (int i = 0; i < pollings.size(); i++) {
            int pid = (int) pollings.get(i).getCourseId();
            String v = courseService.getCourses().get(pid - 1).getSubject();
            if (pollinginfos.indexOf(v) < 0) {
                Pids.add(i+1);
                Pcids.add(pid);
                pollinginfos.add(v);
            }

        }
        List<String> Commentinfos = new ArrayList<String>();
        List<Integer> Ccids = new ArrayList<Integer>();
        List<Integer> Cids = new ArrayList<Integer>();
        for (int i = 0; i < comments.size(); i++) {
            int cid = (int) comments.get(i).getCourseId();
            String v = courseService.getCourses().get(cid - 1).getSubject();
            if (Commentinfos.indexOf(v) < 0) {
                Cids.add(i+1);
                Ccids.add(cid);
                Commentinfos.add(v);
            }

        }
        model.addAttribute("comments", comments);
        model.addAttribute("pollings", pollings);
        model.addAttribute("Commentinfos", Commentinfos);
        model.addAttribute("pollinginfos", pollinginfos);
        model.addAttribute("Pcids", Pcids);
        model.addAttribute("Ccids", Ccids);
        model.addAttribute("Pids", Pids);
        model.addAttribute("Cids", Cids);
        model.addAttribute("courseDatabase", courseService.getCourses());
        return "list";
    }

    public static class Form {

        @NotEmpty(message = "Please enter your user name.")
        private String username;
        @NotEmpty(message = "Please enter your password.")
        @Size(min = 6, max = 15,
                message = "Your password must be between {min} and {max} characters.")
        private String password;
        @NotEmpty(message = "Please enter your full name.")
        private String fullName;
        @NotEmpty(message = "Please enter your phone no.")
        @Size(min = 8, max = 8,
                message = "Your phone no must be {min} characters.")
        private String phoneNumber;
        @NotEmpty(message = "Please enter your address.")
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
    public String create(@ModelAttribute("courseUser") @Valid Form form, BindingResult result) throws IOException {
        userValidator.validate(form, result);
        if (result.hasErrors()) {
            return "addUser";
        }
        CourseUser user = new CourseUser(form.getUsername(), form.getPassword(),
                form.getFullName(), form.getPhoneNumber(), form.getDeliveryAddress(), form.getRoles());
        courseUserRepo.save(user);
        logger.info("User " + form.getUsername() + " created.");

//        return new RedirectView("/user/list", true);
        return "redirect:/user/list";
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
        if (checkvote(pollingId, principal.getName())) {
            CourseUserPolling courseUserPolling = pollingRepository.findById(pollingId).orElse(null);
            CourseUserOption courseUserOption = new CourseUserOption();
            courseUserOption.setPolling(courseUserPolling);
            courseUserOption.setOption(optionForm.getOption());
            courseUserOption.setPollingId(pollingId);
            courseUserOption.setCourse(course);
            courseUserOption.setUser(user);
            course.addOption(courseUserOption);
        } else {
            CourseUserOption courseUserOption = new CourseUserOption();
            logger.info("Done");
            courseUserOption.setOption(checkOption(pollingId, principal.getName()));
        }
        courseRepository.save(course);

        return "redirect:/course/view/" + courseId;
    }
@PostMapping("/{courseId}/{pollingId}/{voted}/changeOption")
    public String changeOption(@PathVariable("courseId") long courseId, @PathVariable("pollingId") long pollingId,
@PathVariable("voted") long voted,
        OptionForm optionForm, Principal principal, ModelMap model) throws IOException, OptionNotFound {
String option=optionForm.getOption();
        courseService.updateOption(voted, option);
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
        int count = 0;
        Long pollingid =pollingId;
        for (int i = 0; i < options.size(); i++) {
            if (pollingid.equals(options.get(i).getPollingId()) &&username.equals(options.get(i).getUsername())) {
                count++;
            }
        }
        if (count <1) {
            return true;
        } else {
            return false;
        }
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
