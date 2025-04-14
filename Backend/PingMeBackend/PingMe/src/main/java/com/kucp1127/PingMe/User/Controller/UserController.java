package com.kucp1127.PingMe.User.Controller;

import com.kucp1127.PingMe.SecurityConfiguration.JwtService;
import com.kucp1127.PingMe.ReminderManagementModule.Model.ReminderManagementModel;
import com.kucp1127.PingMe.ReminderManagementModule.Service.ReminderManagementService;
import com.kucp1127.PingMe.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.kucp1127.PingMe.User.Model.userModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.kucp1127.PingMe.User.DataTransferObjects.UserDTO;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReminderManagementService reminderManagementService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder(12);


    @PostMapping("/register")
    public ResponseEntity<userModel> register(@RequestBody userModel user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userModel createdUser = userService.createUser(user);

        if(createdUser!=null){
            ReminderManagementModel reminderManagementModel = new ReminderManagementModel();
            reminderManagementModel.setUsername(user.getUsername());
            reminderManagementModel.setRemindersList(new ArrayList<>());

            reminderManagementService.save(reminderManagementModel);

        }

        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()) {

            System.out.println(jwtService.generateToken(user.getUsername()));
            return jwtService.generateToken(user.getUsername());
        }
        return "failure";
    }


    // Endpoint to retrieve a user by username
    @GetMapping("/{username}")
    public ResponseEntity<userModel> getUser(@PathVariable String username) {
        Optional<userModel> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to retrieve all users
    @GetMapping
    public ResponseEntity<List<userModel>> getAllUsers() {
        List<userModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint to update an existing user
    @PutMapping
    public ResponseEntity<userModel> updateUser(@RequestBody userModel user) {
        userModel updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint to delete a user by username
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
