package com.spring.microservice.user.controller;

import com.spring.microservice.user.model.User;
import com.spring.microservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:63342", "http://localhost:63343" })
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @return List all users
     */
    @GetMapping("/users")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    /**
     * @param id
     *
     * @return Get a user with id
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {

        User user = userRepository.findUserById(id);

        if (user == null) {
            return new ResponseEntity<String>("No user found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * @param userDetails
     *
     * @implSpec Create a user with userDetails
     */
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User userDetails) {

        User user = userRepository.findAllByUsername(userDetails.getUsername());

        if (user != null) {
            return new ResponseEntity<String>("Haved user with name " + userDetails.getUsername(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<User>(userRepository.save(userDetails), HttpStatus.OK);
    }

    /**
     * @param id, userDetails
     *
     * @implSpec Update a user with id
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {

        User user = userRepository.findUserById(id);

        if (user == null) {
            return new ResponseEntity<String>("No user found for ID " + id, HttpStatus.NOT_FOUND);
        }

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        User updatedUser = userRepository.save(user);

        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    /**
     * @param id
     *
     * @implSpec Delete a user with id
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        User user = userRepository.findUserById(id);

        if (user == null) {
            return new ResponseEntity<String>("No user found for ID " + id, HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
