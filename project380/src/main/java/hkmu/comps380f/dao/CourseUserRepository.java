package hkmu.comps380f.dao;

import hkmu.comps380f.model.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface CourseUserRepository extends JpaRepository<CourseUser, String> {

    public void save(User newUser);
}
