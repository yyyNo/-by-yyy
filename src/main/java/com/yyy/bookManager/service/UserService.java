package com.yyy.bookManager.service;

import com.yyy.bookManager.dao.UserDao;

import com.yyy.bookManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public int addUser(User user){return userDao.addUser(user);}

    public User getUser(String email){return userDao.selectByEmail(email);}

    public User getUser(int uid){return userDao.selectById(uid);}

}
