package web.service;

import web.entity.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    List<User> search(String keyword);
    Optional<User> findById (String id);
    User save(User user);
    User update(User user);
    void delete(String id);
}
