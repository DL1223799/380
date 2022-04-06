package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserCommentRepository;
import hkmu.comps380f.dao.CourseUserRepository;
import hkmu.comps380f.model.CommentForm;
import hkmu.comps380f.model.CourseUser;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.model.CourseUserComment;
import java.io.IOException;
import java.security.Principal;
import javax.annotation.Resource;
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
    CourseUserRepository courseUserRepo;
    @Resource
    private CourseUserCommentRepository courseUserCommentRepository;
    @Resource
    private CourseRepository courseRepository;

    @GetMapping({"", "/list"})
    public String list(ModelMap model) {
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
        CourseUser user = new CourseUser(form.getUsername(),form.getPassword(),
form.getFullName(),form.getPhoneNumber(),form.getDeliveryAddress(),form.getRoles());
        courseUserRepo.save(user);
        return new RedirectView("/user/list", true);
    }


    @GetMapping("/delete/{username}")
    public View deleteCourse(@PathVariable("username") String username) {
        courseUserRepo.delete(courseUserRepo.findById(username).orElse(null));
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

        return "redirect:/course/" + courseId;
    }
}