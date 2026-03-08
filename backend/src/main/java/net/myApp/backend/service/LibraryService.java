package net.myApp.backend.service;

import net.myApp.backend.entity.Library;
import net.myApp.backend.entity.User;
import net.myApp.backend.repository.LibraryRepository;
import net.myApp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Library> getAllLibraries(){
        return libraryRepository.findAll();
    }

    public boolean createLibrary(Library library, String username){
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        boolean isUserExist = userOptional.isPresent();
        if(isUserExist){
            User user = userOptional.get();
            library.setCreated_at(Instant.now());
            libraryRepository.save(library);
            user.setLibrary(library);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
