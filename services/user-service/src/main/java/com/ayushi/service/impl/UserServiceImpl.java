package com.ayushi.service.impl;

import com.ayushi.modal.User;
import com.ayushi.repository.UserRepository;
import com.ayushi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id: " + id));
    }

    @Override
    public List<User> getUsers() throws Exception {
        return userRepository.findAll();
    }
}
