package com.lib.library.service;

import com.lib.library.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    void register(String name,String password);
    void newUser(String name,String password);
    void deleteUser(int id);
    void changePassword(String name,String newPassword);
    Page<User> getUserList(int isDelete, Pageable pageable);
    User getUserById(int id);
    User getUserByName(String name);
}
