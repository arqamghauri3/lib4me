package net.myApp.backend.controller;


import net.myApp.backend.entity.User;
import net.myApp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        Optional<User> user = userService.findUserByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        boolean isCreated = userService.saveUser(user);

        return isCreated ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userid}")
    public ResponseEntity<?> updateUser(@PathVariable Long userid, @RequestBody User newUser){
        Optional<User> user = userService.findUserById(userid);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        boolean isDeleted = userService.deleteUser(id);

        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
