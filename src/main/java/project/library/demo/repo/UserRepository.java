package project.library.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.library.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // Get members only (users with MEMBER role)
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'MEMBER'")
    List<User> findAllMembers();
    
    // Count members only
    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = 'MEMBER'")
    long countMembers();
}
