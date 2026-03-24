package net.myApp.backend.repository;

import net.myApp.backend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByKey(String key);

}
