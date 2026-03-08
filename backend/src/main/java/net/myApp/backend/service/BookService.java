package net.myApp.backend.service;

import net.myApp.backend.entity.Book;
import net.myApp.backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public boolean saveBook(Book book){
        try {
            bookRepository.save(book);
            return true;
        }catch (Exception e){
            System.out.println("Error");
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @Transactional
    public boolean deleteBook(Long id){
        try {
            bookRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Transactional
    public boolean updateBook(Long id, Book book){
        try {
            boolean isBookExists = bookRepository.existsById(id);
            if(isBookExists) {
                bookRepository.save(book);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
