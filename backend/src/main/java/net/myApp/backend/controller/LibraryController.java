package net.myApp.backend.controller;

import net.myApp.backend.entity.Library;
import net.myApp.backend.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<Library>> getAllLibraries(){
        List<Library> libraryList = libraryService.getAllLibraries();
        if(libraryList != null){
            return new ResponseEntity<>(libraryList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createLibrary(@RequestBody Library library, @PathVariable String username){
        boolean isCreated = libraryService.createLibrary(library, username);

        return isCreated ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
