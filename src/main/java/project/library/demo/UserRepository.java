package project.library.demo;

import java.util.Optional;

public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
