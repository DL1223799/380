package hkmu.comps380f.controller;

import hkmu.comps380f.model.Course;
import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserRepository;
import hkmu.comps380f.model.CourseUser;
import hkmu.comps380f.service.CourseService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
@Resource
CourseUserRepository courseuserRepository;
    @GetMapping
    public String index() {
        return "redirect:/course/list";
    }

    @GetMapping("/cslogin")
    public String login() {
        return "login";
    }
//show list
    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public String list(ModelMap model, Principal principal) {
        model.addAttribute("courseDatabase", courseService.getCourses());
        return "list";
    }
@GetMapping("/register")
    public String register(ModelMap model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        CourseUser courseuser = new CourseUser();
        model.addAttribute("courseuser", courseuser);
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(CourseUser courseuser, ModelMap model) {
        if (!courseuserRepository.existsById(courseuser.getUsername())) {
                CourseUser newUser = new CourseUser(courseuser.getUsername(),courseuser.getPassword(),courseuser.getFullName()
,courseuser.getPhoneNumber(),courseuser.getDeliveryAddress(), new String[]{"ROLE_USER"});
                courseuserRepository.save(newUser);
                return "redirect:/login";}
        else return "redirect:/";
    }


}
