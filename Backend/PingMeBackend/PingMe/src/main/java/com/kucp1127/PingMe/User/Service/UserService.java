package com.kucp1127.PingMe.User.Service;


import com.kucp1127.PingMe.User.Model.userModel;
import com.kucp1127.PingMe.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user (signup)
    public userModel createUser(userModel user) {
        return userRepository.save(user);
    }

    // Retrieve a user by username
    public Optional<userModel> getUserByUsername(String username) {
        return userRepository.findById(username);
    }

    // Retrieve all users
    public List<userModel> getAllUsers() {
        return userRepository.findAll();
    }

    // Update an existing user
    public userModel updateUser(userModel user) {
        return userRepository.save(user);
    }

    // Delete a user by username
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }
}
