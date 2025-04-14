package com.kucp1127.PingMe.SecurityConfiguration;


import com.kucp1127.PingMe.User.Model.userModel;
import com.kucp1127.PingMe.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<userModel> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return new UserPrincipal(user.get());
        }

        System.out.println("Not found in Users...");
        throw new UsernameNotFoundException("User 404");
    }

}
