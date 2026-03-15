package net.myApp.backend.service;

import net.myApp.backend.entity.Library;
import net.myApp.backend.entity.User;
import net.myApp.backend.repository.LibraryRepository;
import net.myApp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Library> getAllLibraries(){
        return libraryRepository.findAll();
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public Optional<Library> fetchLibraryByUsername(String username){
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        boolean isUserExist = userOptional.isPresent();
        System.out.println("Is user Exists  "+isUserExist);
        if(isUserExist){
            User user = userOptional.get();
            return userOptional.map(User::getLibrary);
        }
        System.out.println("false");
        return Optional.empty();
    }

    @Transactional
    public boolean updateLibraryByUsername(String username, Library library){

        try {
            Optional<User> userOptional = userRepository.findUserByUsername(username);
            boolean isUserExist = userOptional.isPresent();
            if (isUserExist) {
                User user = userOptional.get();
                Library oldLibrary = user.getLibrary();
                oldLibrary.setBookList(library.getBookList());
                oldLibrary.setModified_at(Instant.now());
                libraryRepository.save(oldLibrary);
                user.setLibrary(oldLibrary);
                userRepository.save(user);
                return true;
            }
        }catch (Exception e){
            System.out.println("Error " +e);
        }
        return false;
    }

    @Transactional
    public boolean deleteLibrary(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        user.setLibrary(null);
        return true;
    }
}
