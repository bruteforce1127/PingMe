package com.kucp1127.PingMe.User.Repository;

import com.kucp1127.PingMe.User.Model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<userModel, String> {
    // You can define additional custom queries here if needed
}
