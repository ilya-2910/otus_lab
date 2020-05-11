package ru.otus.servlet;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.core.model.User;
import ru.otus.front.FrontendService;

@Controller
public class MessageController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final FrontendService frontendService;

  public MessageController(FrontendService frontendService, SimpMessagingTemplate simpMessagingTemplate) {
    this.frontendService = frontendService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @MessageMapping("/addUser")
  public void addUser(User user) {
    frontendService.saveUser(user,  data -> {
      simpMessagingTemplate.convertAndSend("/topic/changeUser", user.getId());
    });
  }

}
