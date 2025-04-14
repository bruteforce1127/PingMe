package com.kucp1127.PingMe.ReminderManagementModule.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReminderManagementModel {


    @Id
    private String username;

    @OneToMany(mappedBy = "reminderManagementModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reminders> remindersList =  new ArrayList<>();

}
