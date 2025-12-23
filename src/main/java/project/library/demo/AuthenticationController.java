package project.library.demo;

import jakarta.validation.Valid;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;  // Your JWT utility class

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
        // Create new user
        User user = new User();  // Use your existing User entity (not UserEntity)
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // fixed typo: getPasword → getPassword
        user.setFullName(registerRequest.getFullName());  // optional
        user.setEmail(registerRequest.getEmail());        // optional

        // Save to database
        userRepository.save(user);


        String token = jwtUtil.generateToken(user.getUsername());

        AuthResponse authResponse = new AuthResponse(registerRequest.getUsername(), token);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}