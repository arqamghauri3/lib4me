package net.myApp.backend.repository;

import net.myApp.backend.entity.Library;
import net.myApp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    Optional<Library> findLibraryByUser(User user);
}
