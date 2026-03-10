package net.myApp.backend.controller;


import net.myApp.backend.entity.User;
import net.myApp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/id/{userid}")
    public ResponseEntity<User> getUserById(@PathVariable Long userid){
        Optional<User> user = userService.findUserById(userid);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fetchUser")
    public ResponseEntity<User> getUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findUserByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(user.getRoles() == null) user.setRoles("USER");
        boolean isCreated = userService.saveUser(user);
        return isCreated ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findUserByUsername(username);
        if(user.isPresent()){
           User oldUser = user.get();
           oldUser.setEmail(newUser.getEmail());
           oldUser.setUsername(newUser.getUsername());
           oldUser.setPassword(newUser.getPassword());
           userService.saveUser(oldUser);
           return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isDeleted = userService.deleteUser(username);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
