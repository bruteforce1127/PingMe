package com.kucp1127.PingMe.GiftSuggestion.Controller;


import com.kucp1127.PingMe.GiftSuggestion.Service.GiftSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class GiftSuggestionController {

    @Autowired
    private GiftSuggestionService giftSuggestionService;

    @GetMapping("/gifts/{description}")
    private ResponseEntity<?> sendGiftsNotification(@PathVariable String description){

        String notification = "Based on the gift list: [LIST_OF_GIFTS] and description: "+description+", " +
                "generate a concise notification that recommends a gift idea. Include a suitable emoji " +
                "and ensure the message is short and punchy, with no extra text.";


        return ResponseEntity.ok(giftSuggestionService.sendNotification(notification));
    }

    @GetMapping("/places/{description}")
    private ResponseEntity<?> sendPlacesNotification(@PathVariable String description){

        String notification = "Based on the gift list: [LIST_OF_GIFTS] and description: "+description+", craft a brief " +
                "notification recommending a place to visit with places names. The message should be engaging, include relevant emojis," +
                " and be very concise with no additional labels.";

        return ResponseEntity.ok(giftSuggestionService.sendNotification(notification));
    }

}
