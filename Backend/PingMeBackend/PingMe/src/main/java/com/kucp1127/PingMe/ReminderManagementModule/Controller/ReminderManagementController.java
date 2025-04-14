package com.kucp1127.PingMe.ReminderManagementModule.Controller;


import com.kucp1127.PingMe.ReminderManagementModule.Model.ReminderManagementModel;
import com.kucp1127.PingMe.ReminderManagementModule.Model.Reminders;
import com.kucp1127.PingMe.ReminderManagementModule.Service.ReminderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminderManagement")
@CrossOrigin("*")
public class ReminderManagementController {

    @Autowired
    private ReminderManagementService reminderManagementService;


    @PostMapping
    public ResponseEntity<ReminderManagementModel> createOrUpdateModel(@RequestBody ReminderManagementModel model) {
        ReminderManagementModel savedModel = reminderManagementService.save(model);
        return ResponseEntity.ok(savedModel);
    }


    @GetMapping("/{username}")
    public ResponseEntity<ReminderManagementModel> getModel(@PathVariable String username) {
        return reminderManagementService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{username}")
    public ResponseEntity<ReminderManagementModel> addReminder(@PathVariable String username,
                                                               @RequestBody Reminders reminder) {
        ReminderManagementModel updatedModel = reminderManagementService.addReminder(username, reminder);
        return ResponseEntity.ok(updatedModel);
    }


    @DeleteMapping("/{username}/reminders/{reminderId}")
    public ResponseEntity<ReminderManagementModel> deleteReminder(@PathVariable String username,
                                                                  @PathVariable Long reminderId) {
        return reminderManagementService.removeReminder(username, reminderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
