package com.kucp1127.PingMe.GiftSuggestion.Service;


import com.kucp1127.PingMe.OllamaDeepSeek.Service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftSuggestionService {

    @Autowired
    private OllamaService ollamaService;
    public String sendNotification(String description) {
        return ollamaService.getAnswer(description);
    }
}
