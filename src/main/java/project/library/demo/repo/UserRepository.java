package project.library.demo.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.library.demo.entity.User;

public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    //long countByRolesContaining(String role); // or however you store members

   // long countByCreatedAtAfter(LocalDateTime startOfMonth); // new members this month

}
