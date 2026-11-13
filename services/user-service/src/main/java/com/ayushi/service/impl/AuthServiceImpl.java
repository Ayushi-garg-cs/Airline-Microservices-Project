package com.ayushi.service.impl;

import com.ayushi.config.JwtProvider;
import com.ayushi.enums.UserRole;
import com.ayushi.mapper.UserMapper;
import com.ayushi.modal.User;
import com.ayushi.payload.dto.UserDTO;
import com.ayushi.payload.response.AuthResponse;
import com.ayushi.repository.UserRepository;
import com.ayushi.service.AuthService;
import com.ayushi.service.CustomUserDetailService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailsService;

    /*
    Steps:
        1. Check if email already exists
        2. Encode password using BCrypt
        3. Save user in database
        4. Generate JWT token
        5. Return token and user information
    */
    @Override
    public AuthResponse signup(UserDTO req) throws Exception {
        User existingUser = userRepository.findByEmail(req.getEmail());
        if (existingUser != null) {
            throw new Exception("Email already registered");
        }

        if (req.getRole() == UserRole.ROLE_SYSTEM_ADMIN) {
            throw new Exception("Cannot register as SYSTEM_ADMIN");
        }

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setPassword(passwordEncoder.encode(req.getPassword()));
        createdUser.setPhone(req.getPhone());
        createdUser.setFullName(req.getFullName());
        createdUser.setRole(req.getRole());
        createdUser.setLastLogin(LocalDateTime.now());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(), savedUser.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome " + savedUser.getFullName());
        response.setMessage("Registration successful");
        response.setUser(UserMapper.toDTO(savedUser));
        response.setJwt(jwt);
        return response;
    }

    /*
    Steps:
        1. Load user by email
        2. Compare password with BCrypt
        3. Update `lastLogin` time
        4. Generate JWT token
        5. Return token and user information
    */
    @Override
    public AuthResponse login(String email, String password) throws Exception {
        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(email);
        String token = jwtProvider.generateToken(authentication, user.getId());

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setTitle("Login successful");
        response.setMessage("Welcome back " + user.getFullName());
        response.setJwt(token);
        response.setUser(UserMapper.toDTO(user));
        return response;
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(email);
        if (userDetails == null) {
            throw new Exception("User not found with email: " + email);
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(
                email, null, userDetails.getAuthorities());
    }
}
