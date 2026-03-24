package net.myApp.backend.service;

import net.myApp.backend.entity.Author;
import net.myApp.backend.entity.Book;
import net.myApp.backend.entity.Genre;
import net.myApp.backend.repository.AuthorRepository;
import net.myApp.backend.repository.BookRepository;
import net.myApp.backend.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public Book saveBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().getKey() == null) {
            throw new IllegalArgumentException("Author is required");
        }
        Optional<Book> bookOptional = bookRepository.findBookByKey(book.getKey());
        Book bookToSave = bookOptional.orElse(book);
        Author incomingAuthor = book.getAuthor();
        Author author = authorRepository.findAuthorByKey(incomingAuthor.getKey())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setKey(incomingAuthor.getKey());
                    newAuthor.setName(incomingAuthor.getName());
                    return authorRepository.save(newAuthor);
                });

        bookToSave.setAuthor(author);

        if (book.getGenreList() != null) {
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
            bookToSave.setGenreList(resolvedGenres);
        } else {
            bookToSave.setGenreList(new HashSet<>());
        }

        return bookRepository.save(bookToSave);


    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @Transactional
    public boolean deleteBook(Long id){
            bookRepository.deleteById(id);
            return true;
    }

    @Transactional
    public boolean updateBook(Long id, Book book){
            boolean isBookExists = bookRepository.existsById(id);
            if(isBookExists) {
                bookRepository.save(book);
                return true;
            }
            return false;
    }
}
