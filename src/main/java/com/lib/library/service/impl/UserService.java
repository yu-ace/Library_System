package com.lib.library.service.impl;

import com.lib.library.dao.IUserDao;
import com.lib.library.model.User;
import com.lib.library.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public void register(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIdentity("读者");
        user.setIsDelete(0);
        userDao.save(user);
    }

    @Override
    public void newUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIdentity("管理员");
        user.setIsDelete(0);
        userDao.save(user);
    }

    @Override
    public void deleteUser(int id) {
        User user = userDao.findById(id);
        user.setIsDelete(1);
        userDao.save(user);
    }

    @Override
    public void changePassword(String name, String newPassword) {
        User user = userDao.findByName(name);
        user.setPassword(newPassword);
        userDao.save(user);
    }

    @Override
    public Page<User> getUserList(int isDelete, Pageable pageable) {
        return userDao.findByIsDelete(isDelete,pageable);
    }

    @Override
    public User getUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.findByName(name);
    }
}
