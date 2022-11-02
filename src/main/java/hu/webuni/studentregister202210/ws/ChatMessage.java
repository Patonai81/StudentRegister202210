package hu.webuni.studentregister202210.ws;

import lombok.Data;

@Data
public class ChatMessage {
    private String sender;
    private long courseId;
    private String messageText;
}
