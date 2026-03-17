package net.myApp.backend.service;


import net.myApp.backend.entity.User;
import net.myApp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public boolean saveUser(User user){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
    }

    @Transactional
    public boolean deleteUser(String username){
            userRepository.deleteUserByUsername(username);
            return true;
    }

    public List<User> getAll(){
            return userRepository.findAll();
    }

}
