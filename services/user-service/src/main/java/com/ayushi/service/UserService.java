package com.ayushi.service;

import com.ayushi.modal.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getUserByEmail(String email) throws Exception;
    User getUserById(Long id) throws Exception;
    List<User> getUsers() throws Exception;
}

