package net.myApp.backend.controller;

import net.myApp.backend.entity.Book;
import net.myApp.backend.entity.Library;
import net.myApp.backend.service.BookService;
import net.myApp.backend.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;
    @Autowired
    private BookService bookService;

//    @GetMapping
//    public ResponseEntity<List<Library>> getAllLibraries(){
//        List<Library> libraryList = libraryService.getAllLibraries();
//        if(libraryList != null){
//            return new ResponseEntity<>(libraryList, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



    @PostMapping
    public ResponseEntity<?> createLibrary(@RequestBody Library library){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isCreated = libraryService.createLibrary(library, username);
        return isCreated ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLibraryByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isCreated = libraryService.deleteLibrary(username);
        return isCreated ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/fetchLibrary")
    public ResponseEntity<?> getLibraryByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("username");
        Optional<Library> libraryOptional = libraryService.fetchLibraryByUsername(username);
        boolean isPresent = libraryOptional.isPresent();
        System.out.println(isPresent);
        return isPresent ? new ResponseEntity<Library>(libraryOptional.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("add-books")
    public ResponseEntity<?> addBooksInLibrary(@RequestBody Book book){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Book createdBook = bookService.saveBook(book);
        Optional<Library> libraryOptional = libraryService.fetchLibraryByUsername(username);
        boolean isPresent = libraryOptional.isPresent();
        boolean isCreated = false;
        if(isPresent){
            isCreated = libraryService.addBookInLibrary(createdBook,libraryOptional.get());
        }

        return isCreated ? new ResponseEntity<Library>(libraryOptional.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
