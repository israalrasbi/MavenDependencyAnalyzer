package com.isra.security.dependency_analyzer.Services;

import com.isra.security.dependency_analyzer.DTOs.SignInRequestDTO;
import com.isra.security.dependency_analyzer.DTOs.SignUpRequestDTO;
import com.isra.security.dependency_analyzer.Entities.User;
import com.isra.security.dependency_analyzer.Exceptions.InvalidCredentialsException;
import com.isra.security.dependency_analyzer.Exceptions.UserAlreadyExistsException;
import com.isra.security.dependency_analyzer.Repositories.UserRepository;
import com.isra.security.dependency_analyzer.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String signIn(SignInRequestDTO dto) throws Exception {
        // Find user by username
        User user = userRepository.findByUserName(dto.getUserName());
        if (user == null) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        // Check password match
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Generate JWT token
        return jwtUtil.generateToken(user.getUserName(), user.getEmail(), user.getRole().name());
    }

    public void signUp(SignUpRequestDTO dto) throws Exception {
        if (userRepository.findByUserName(dto.getUserName()) != null) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

    }
}