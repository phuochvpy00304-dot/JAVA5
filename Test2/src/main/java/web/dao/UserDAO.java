package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import web.entity.User;

import java.util.List;

public interface UserDAO extends JpaRepository<User,String> {
    List<User> findByIdContainingIgnoreCaseOrFullnameContainingIgnoreCase(String id, String fullname);
}
