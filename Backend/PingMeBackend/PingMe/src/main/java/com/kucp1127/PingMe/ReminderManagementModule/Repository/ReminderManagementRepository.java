package com.kucp1127.PingMe.ReminderManagementModule.Repository;

import com.kucp1127.PingMe.ReminderManagementModule.Model.ReminderManagementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderManagementRepository extends JpaRepository<ReminderManagementModel, String> {
    // Additional query methods can be defined here if needed
}
