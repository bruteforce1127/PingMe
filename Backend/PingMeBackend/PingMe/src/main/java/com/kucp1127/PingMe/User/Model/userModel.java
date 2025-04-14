package com.kucp1127.PingMe.User.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userModel {
    @Id
    private String username;
    private String fullName;
    private String password;
    private String mail;
}
