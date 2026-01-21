package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDAO;
import web.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserDAO dao;
    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public List<User> search(String keyword) {
        if(keyword==null|| keyword.isEmpty()){
            return dao.findAll();
        }
        return dao.findByIdContainingIgnoreCaseOrFullnameContainingIgnoreCase(keyword,keyword);
    }


    @Override
    public Optional<User> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public User save(User user) {
        return dao.save(user);
    }

    @Override
    public User update(User user) {
        return dao.save(user);
    }

    @Override
    public void delete(String id) {
    dao.deleteById(id);
    }
}
