package web.service;

import web.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    List<User> search(String kw);
    Optional<User> findById(String id);
    User create(User user);
    User update(User user);
    void delete(String id);
}
