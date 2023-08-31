package org.example.service;

import org.example.Exceptions.ResourceNotFoundException;
import org.example.entities.User;
import org.example.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(BookingService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        logger.info("User with name " + user.getName() + " successfully added.");
        return userRepository.save(user);
    }

    @Override
    public User updateInfo(User user) {
        int id = user.getUserId();
        User u = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        u.setUserName(user.getUserName());
        u.setName(user.getName());
        u.setAddress(user.getAddress());
        userRepository.save(u);
        logger.info("User with name " + user.getName() + " successfully updated.");
        return u;
    }

    @Override
    public ResponseEntity<String> updateInfoLogic(User user) {
        updateInfo(user);
        return new ResponseEntity<>("User with username " + user.getUserName() + " updated", HttpStatus.OK);
    }
}
