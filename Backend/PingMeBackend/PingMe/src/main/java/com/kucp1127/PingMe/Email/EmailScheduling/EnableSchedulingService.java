package com.kucp1127.PingMe.Email.EmailScheduling;

import com.kucp1127.PingMe.OllamaDeepSeek.Service.OllamaService;
import com.kucp1127.PingMe.Email.DTO.EmailDTO;
import com.kucp1127.PingMe.Email.EmailController.EmailController;
import com.kucp1127.PingMe.ReminderManagementModule.Model.ReminderManagementModel;
import com.kucp1127.PingMe.ReminderManagementModule.Model.Reminders;
import com.kucp1127.PingMe.ReminderManagementModule.Service.ReminderManagementService;
import com.kucp1127.PingMe.User.Model.userModel;
import com.kucp1127.PingMe.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnableSchedulingService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailController emailController;

    @Autowired
    private ReminderManagementService reminderManagementService;

    @Autowired
    private OllamaService ollamaService;

    @Scheduled(cron = "0 0 7 * * *")
    public void sendScheduledEmails() {

        List<userModel> userModels = userService.getAllUsers();
        List<EmailDTO> usernames = new ArrayList<>();
        for(userModel user :userModels){
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setEmail(user.getMail());
            emailDTO.setUsername(user.getUsername());
            usernames.add(emailDTO);
        }

        for(EmailDTO users : usernames){
            String username = users.getUsername();

            Optional<ReminderManagementModel> reminderManagementModel = reminderManagementService.findByUsername(username);

            if(reminderManagementModel.isPresent()){
                List<Reminders> reminders = reminderManagementModel.get().getRemindersList();
                for(Reminders reminders1 : reminders){
                        String description = reminders1.getDescription();
                        if(Objects.equals(description, "")){
                            description=reminders1.getTitle();
                        }
                        String bodyPrompt = "Based on the following details—username: [ "+username+" ], " +
                                "description: [ "+description+" ], reminder date: [ "+reminders1.getDate()+" ]," +
                                " and current date: [ "+ LocalDate.now() +"]—compose a warm and " +
                                "engaging email reminder. In the message, dynamically " +
                                "refer to the event timing using phrases such as 'today' " +
                                "if the reminder date equals the current date, 'tomorrow' if it's one day away, or 'in 1 week' " +
                                "if the event is seven days later. Include friendly suggestions related to the event; for example, " +
                                "if the description implies a meeting, mention something like 'don't forget to take your laptop' (" +
                                "or another appropriate item based on the description). Make sure to include relevant " +
                                "emojis to enhance the tone, and output only the final email body text without any labels" +
                                " or extra formatting. And in Best Regards Team 'PingMe' ";
                        String subjectPrompt = "Based on the following information—username: [ "+username+" ] and description: [ "+description+" ]—generate a concise, " +
                                "compelling, and emoji-enhanced email subject line. " +
                                "Return only the subject text without any labels or additional formatting";
                        String body = ollamaService.getAnswer(bodyPrompt);
                        String subject = ollamaService.getAnswer(subjectPrompt);
                    LocalDate reminderLocalDate = reminders1.getDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                        if(reminders1.getPriority().equals("High") &&
                                (reminderLocalDate.equals(LocalDate.now())
                                        || reminderLocalDate.equals(LocalDate.now().plusDays(7)) ||
                                        reminderLocalDate.equals(LocalDate.now().plusDays(1)) ||
                                        reminderLocalDate.equals(LocalDate.now().plusDays(3))) ){
                            emailController.sendEmail(users.getEmail(),subject,body);
                        }
                        else if(reminders1.getPriority().equals("Medium") &&
                                (reminderLocalDate.equals(LocalDate.now()) ||
                                        reminderLocalDate.equals(LocalDate.now().plusDays(3)) ||
                                        reminderLocalDate.equals(LocalDate.now().plusDays(1)) ) ){
                            emailController.sendEmail(users.getEmail(),subject,body);
                        }
                        else if(reminders1.getPriority().equals("Low") &&
                                (reminderLocalDate.equals(LocalDate.now()) ||
                                        reminderLocalDate.equals(LocalDate.now().plusDays(1)) ) ){
                            emailController.sendEmail(users.getEmail(),subject,body);
                        }
                }

            }

        }
    }
}
