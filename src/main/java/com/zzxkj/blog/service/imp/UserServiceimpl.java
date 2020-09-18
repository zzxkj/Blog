package com.zzxkj.blog.service.imp;

import com.zzxkj.blog.Entity.User;
import com.zzxkj.blog.dao.UserRepository;
import com.zzxkj.blog.service.UserService;
import com.zzxkj.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
