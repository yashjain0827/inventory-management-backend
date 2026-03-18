package com.yash.inventory.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yash.inventory.dto.LoginRequest;
import com.yash.inventory.dto.RegisterRequest;
import com.yash.inventory.entity.Company;
import com.yash.inventory.entity.Role;
import com.yash.inventory.entity.User;
import com.yash.inventory.exception.ResourceAlreadyExistsException;
import com.yash.inventory.exception.ResourceNotFoundException;
import com.yash.inventory.repository.CompanyRepository;
import com.yash.inventory.repository.UserRepository;
import com.yash.inventory.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        // 🔥 Fetch Company
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // ✅ Enum
                .company(company) // ✅ Multi-tenant
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    // ✅ LOGIN
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 🔥 Add claims in JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("companyId", user.getCompany().getId());
        claims.put("role", user.getRole().name());

        return jwtUtil.generateToken(claims, user.getEmail());
    }
}