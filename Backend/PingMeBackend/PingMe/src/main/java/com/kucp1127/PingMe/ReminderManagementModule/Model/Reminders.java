package com.kucp1127.PingMe.ReminderManagementModule.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reminders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @JsonFormat(pattern = "MMM dd, yyyy", locale = "en")
    private Date date;
    private String priority;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    @JsonBackReference
    private ReminderManagementModel reminderManagementModel;


}
