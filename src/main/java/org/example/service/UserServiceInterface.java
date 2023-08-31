package org.example.service;

import org.example.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceInterface {

    List<User> listUsers();
    User addUser(User user);
    User updateInfo(User user);
    ResponseEntity<String>updateInfoLogic(User user);

}
