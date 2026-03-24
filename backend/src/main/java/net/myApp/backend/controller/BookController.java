package net.myApp.backend.controller;

import net.myApp.backend.entity.Book;
import net.myApp.backend.entity.Genre;
import net.myApp.backend.service.BookService;
import net.myApp.backend.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<?> saveBook(@RequestBody Book book){
        Book bookCreated = bookService.saveBook(book);
        if(bookCreated != null) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> bookList = bookService.getBooks();
        if(bookList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId){
        boolean isDeleted = bookService.deleteBook(bookId);
        if(isDeleted) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody Book book){
        boolean isUpdate = bookService.updateBook(bookId, book);
        return isUpdate ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
