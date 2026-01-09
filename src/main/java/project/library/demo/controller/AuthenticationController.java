package project.library.demo.controller;

import jakarta.validation.Valid;
import project.library.demo.authorize.AuthResponse;
import project.library.demo.authorize.LoginRequest;
import project.library.demo.authorize.LoginResponse;
import project.library.demo.authorize.RegisterRequest;
import project.library.demo.config.JwtUtil;
import project.library.demo.entity.User;
import project.library.demo.entity.Role;
import project.library.demo.entity.ERole;
import project.library.demo.repo.UserRepository;
import project.library.demo.repo.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            LoginResponse response = new LoginResponse(
                userDetails.getUsername(),
                "Login successful"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LoginResponse errorResponse = new LoginResponse(
                null,
                "Invalid username or password"
            );
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @PostMapping("/api/register")
    public ResponseEntity<AuthResponse> registerAPI(@Valid @RequestBody RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(null, "Username already exists"));
        }

        // Create new user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());

        // Assign default MEMBER role
        Role memberRole = roleRepository.findByName(ERole.MEMBER)
            .orElseThrow(() -> new RuntimeException("Error: Role MEMBER is not found in database."));
        
        user.addRole(memberRole);

        // Save to database
        userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(user.getUsername());

        AuthResponse authResponse = new AuthResponse(registerRequest.getUsername(), token);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}