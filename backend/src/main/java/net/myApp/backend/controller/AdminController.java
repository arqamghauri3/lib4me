package net.myApp.backend.controller;


import net.myApp.backend.entity.User;
import net.myApp.backend.repository.UserRepository;
import net.myApp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList = userService.getAll();
        if(userList != null && !userList.isEmpty()){
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> saveAdminUser(@RequestBody User user){
        if(user.getRoles() == null) user.setRoles("ADMIN");
        boolean isCreated = userService.saveUser(user);
        return isCreated ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
