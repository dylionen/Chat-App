package pl.training.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.training.chat.model.Message;
import pl.training.chat.services.LoggedUserService;

@RestController
@Slf4j
@CrossOrigin
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LoggedUserService loggedUserService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, LoggedUserService loggedUserService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.loggedUserService = loggedUserService;
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        log.info(message + " to : " + to);
        if (loggedUserService.userExists(to)) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }
    }
}
