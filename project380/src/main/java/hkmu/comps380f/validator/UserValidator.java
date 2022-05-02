/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.validator;

import hkmu.comps380f.controller.CourseUserController.Form;
import hkmu.comps380f.dao.CourseUserRepository;
import hkmu.comps380f.model.CourseUser;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author DavidLam
 */
@Component
public class UserValidator implements Validator {

    @Resource
    CourseUserRepository courseUserRepo;

    @Override
    public boolean supports(Class<?> type) {
        return Form.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Form user = (Form) o;
//        ValidationUtils.rejectIfEmpty(errors, "confirm_password", "",
//                "Please confirm your password.");
//        if (!user.getPassword().equals(user.getConfirm_password())) {
//            errors.rejectValue("confirm_password", "",
//                    "Password does not match.");
//        }
        if (user.getUsername().equals("")) {
            return;
        }
        CourseUser courseUser = courseUserRepo.findById(user.getUsername())
                .orElse(null);
        if (courseUser != null) {
            errors.rejectValue("username", "", "User already exists.");
        }
    }
}
