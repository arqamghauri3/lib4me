package net.myApp.backend.repository;

import net.myApp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findUserByUsername(String username);

    void deleteUserByUsername(String username);
}
