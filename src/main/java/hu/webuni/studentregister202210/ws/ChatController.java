package hu.webuni.studentregister202210.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    @PreAuthorize("#message.sender == authentication.principal.userName")
    private void send(ChatMessage message){
        simpMessagingTemplate.convertAndSend("/topic/corseChat/"+message.getCourseId(),message.getMessageText());
    }
}
