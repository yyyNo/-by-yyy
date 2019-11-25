package com.yyy.bookManager.service;


import com.yyy.bookManager.model.User;
import org.springframework.stereotype.Service;
import com.yyy.bookManager.utils.ConcurrentUtils;

@Service
public class HostHolder {
    public User getUser(){ return ConcurrentUtils.getHost();}

    public void setUser(User user){ ConcurrentUtils.setHost(user);}


}
