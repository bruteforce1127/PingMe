package com.kucp1127.PingMe.PlacesSuggestion.Controller;

import com.kucp1127.PingMe.OllamaDeepSeek.Service.OllamaService;
import com.kucp1127.PingMe.Email.EmailController.EmailController;
import com.kucp1127.PingMe.PlacesSuggestion.Service.PlacesSuggestionService;
import com.kucp1127.PingMe.User.Model.userModel;
import com.kucp1127.PingMe.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class PlacesSuggestionController {

    @Autowired
    private PlacesSuggestionService placesSuggestionService;

    @Autowired
    private EmailController emailController;

    @Autowired
    private UserService userService;

    @Autowired
    private OllamaService ollamaService;

    @GetMapping("/get/{username}/{prompt}")
    public ResponseEntity<?> getChatOutput(@PathVariable  String username, @PathVariable String prompt){
        String output = placesSuggestionService.generateChat(username, prompt)
                .block();


        String bodyPrompt = "Using the provided content: [ "+ output + " ] create a 4 to 5 lines  with places names , " +
                "create a friendly and engaging email body with relevant emojis. Keep the tone natural and personal. " +
                "Only output the email body text without any extra labels or formatting.";


        String subjectPrompt = "Using the provided information—username: [ "+username+" ] and location: [  "+prompt +" ]—generate" +
                " a concise and engaging email subject line that suggests some good places in this location. Incorporate " +
                "friendly language and suitable emojis, and output only the subject text without any extra labels or formatting.";

        Optional<userModel> user = userService.getUserByUsername(username);

        String body = ollamaService.getAnswer(bodyPrompt);
        String subject = ollamaService.getAnswer(subjectPrompt);

        if(user.isPresent()){
            String email = user.get().getMail();
            return ResponseEntity.ok(emailController.sendEmail(email,subject,body));
        }

        return ResponseEntity.internalServerError().body("Error occurred");

    }
}
