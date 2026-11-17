package com.ayushi.controller;

import com.ayushi.mapper.UserMapper;
import com.ayushi.modal.User;
import com.ayushi.payload.dto.UserDTO;
import com.ayushi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @RequestHeader("X-User-Email") String email) throws Exception {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long userId) throws Exception {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserDTO>> getUsers() throws Exception {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(UserMapper.toDTOList(users));
    }
}
