
package project.library.demo.service;

import org.springframework.stereotype.Service;
import project.library.demo.entity.User;
import project.library.demo.repo.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    public User findByUsername(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));
}

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public long countMembers() {
        return userRepository.count();
    }
}