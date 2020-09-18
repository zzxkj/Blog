package com.zzxkj.blog.service;

import com.zzxkj.blog.Entity.User;

public interface UserService {

    User checkUser(String username ,String  password);
}
