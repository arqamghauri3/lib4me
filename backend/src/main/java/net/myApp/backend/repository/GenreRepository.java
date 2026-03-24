package net.myApp.backend.repository;

import net.myApp.backend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByKey(String key);
}
