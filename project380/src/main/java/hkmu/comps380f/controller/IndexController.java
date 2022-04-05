package hkmu.comps380f.controller;

import hkmu.comps380f.model.Course;
import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.dao.CourseUserRepository;
import hkmu.comps380f.service.CourseService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

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


}
