package net.myApp.backend.service;

import net.myApp.backend.entity.Book;
import net.myApp.backend.entity.Genre;
import net.myApp.backend.repository.BookRepository;
import net.myApp.backend.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;


    @Transactional
    public void addGenreForBook(Book book) {
        if (book.getGenreList() == null) {
            book.setGenreList(new HashSet<>());
            bookRepository.save(book);
            return;
        }

        Set<Genre> resolvedGenres = new HashSet<>();

        for (Genre incomingGenre : book.getGenreList()) {
            if (incomingGenre == null || incomingGenre.getKey() == null) {
                continue;
            }

            Genre genre = genreRepository.findGenreByKey(incomingGenre.getKey())
                    .orElseGet(() -> {
                        Genre newGenre = new Genre();
                        newGenre.setKey(incomingGenre.getKey());
                        newGenre.setName(incomingGenre.getName());
                        return genreRepository.save(newGenre);
                    });

            resolvedGenres.add(genre);
        }

        book.setGenreList(resolvedGenres);
        bookRepository.save(book);
    }
}
