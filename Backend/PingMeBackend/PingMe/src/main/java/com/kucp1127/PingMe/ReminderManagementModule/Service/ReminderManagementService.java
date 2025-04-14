package com.kucp1127.PingMe.ReminderManagementModule.Service;

import com.kucp1127.PingMe.ReminderManagementModule.Model.ReminderManagementModel;
import com.kucp1127.PingMe.ReminderManagementModule.Model.Reminders;
import com.kucp1127.PingMe.ReminderManagementModule.Repository.ReminderManagementRepository;
import com.kucp1127.PingMe.ReminderManagementModule.Repository.RemindersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ReminderManagementService {

    @Autowired
    private ReminderManagementRepository reminderManagementRepository;

    @Autowired
    private RemindersRepository remindersRepository;

    public ReminderManagementModel save(ReminderManagementModel model) {
        return reminderManagementRepository.save(model);
    }

    public Optional<ReminderManagementModel> findByUsername(String username) {
        return reminderManagementRepository.findById(username);
    }

    public ReminderManagementModel addReminder(String username, Reminders reminder) {
        ReminderManagementModel model = reminderManagementRepository.findById(username)
                .orElse(new ReminderManagementModel(username, new ArrayList<>()));
        reminder.setReminderManagementModel(model);
        model.getRemindersList().add(reminder);
        return reminderManagementRepository.save(model);
    }

    public Optional<ReminderManagementModel> removeReminder(String username, Long reminderId) {
        Optional<ReminderManagementModel> optionalModel = reminderManagementRepository.findById(username);
        if (optionalModel.isPresent()) {
            ReminderManagementModel model = optionalModel.get();
            model.getRemindersList().removeIf(reminder -> reminder.getId().equals(reminderId));
            return Optional.of(reminderManagementRepository.save(model));
        }
        return Optional.empty();
    }
}
